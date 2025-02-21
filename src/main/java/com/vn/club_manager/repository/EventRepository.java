package com.vn.club_manager.repository;

import com.vn.club_manager.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository(value = "eventRepository")
public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findByIdAndClubId(Long id, Long clubId);

    @Query(value = "SELECT e FROM Event e " +
            "WHERE (:name IS NULL OR :name = '' OR (e.name LIKE CONCAT('%', :name, '%'))) " +
            "AND e.club.id = :clubId ")
    Page<Event> searchEventClub(String name, Long clubId, Pageable pageable);

    @Query(value = "SELECT e FROM Event e " +
            "WHERE (:name IS NULL OR :name = '' OR (e.name LIKE CONCAT('%', :name, '%'))) ")
    Page<Event> searchEvents(String name, Pageable pageable);

    @Query(value = "SELECT e FROM Event e " +
            "WHERE (:name IS NULL OR :name = '' OR (e.name LIKE CONCAT('%', :name, '%'))) " +
            "AND e.club.id IN " +
            "(SELECT m.club.id FROM Member m WHERE m.user.id = :userId)")
    Page<Event> searchEventsUser(String name, Long userId, Pageable pageable);

    @Query(value = "SELECT e FROM Event e " +
            "JOIN e.users u " +
            "WHERE u.id = :userId " +
            "AND e.club.id = :clubId ")
    Page<Event> getEventsOfMember(Long userId, Long clubId, Pageable pageable);
}
