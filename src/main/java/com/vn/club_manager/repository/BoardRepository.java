package com.vn.club_manager.repository;

import com.vn.club_manager.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository(value = "boardRepository")
public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findAllByClubId(Long id);

    Optional<Board> findByIdAndClubId(Long id, Long clubId);
}
