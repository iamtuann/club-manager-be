package com.vn.club_manager.repository;

import com.vn.club_manager.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "eventRepository")
public interface EventRepository extends JpaRepository<Event, Long> {
}
