package com.vn.club_manager.repository;

import com.vn.club_manager.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "clubRepository")
public interface ClubRepository extends JpaRepository<Club, Long> {
}
