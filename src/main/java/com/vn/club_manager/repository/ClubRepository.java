package com.vn.club_manager.repository;

import com.vn.club_manager.entity.Club;
import com.vn.club_manager.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository(value = "clubRepository")
public interface ClubRepository extends JpaRepository<Club, Long> {

    Club findClubById(long id);

    boolean existsByIdAndManagerId(long id, long managerId);

    @Query(value = "SELECT c FROM Club c " +
            "WHERE (:name IS NULL OR :name = '' OR (c.name LIKE CONCAT('%', :name, '%'))) " )
    Page<Club> searchClubs(@Param("name") String name,
                             Pageable pageable);

    @Query(value = "SELECT c.members FROM Club c " +
            "JOIN c.members m " +
            "WHERE (:name IS NULL OR :name = '' " +
            "OR LOWER(m.user.firstName) LIKE LOWER(CONCAT('%', :name, '%')) " +
            "OR LOWER(m.user.lastName) LIKE LOWER(CONCAT('%', :name, '%')) " +
            "OR LOWER(CONCAT(m.user.firstName, ' ', m.user.lastName)) LIKE LOWER(CONCAT('%', :name, '%'))) " +
            "AND c.id = :clubId " +
            "AND m.status = 1" )
    Page<Member> getMembersByClubId(@Param("clubId") Long clubId,
                                    String name,
                                    Pageable pageable);

    Long id(Long id);
}
