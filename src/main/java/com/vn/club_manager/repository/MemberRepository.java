package com.vn.club_manager.repository;

import com.vn.club_manager.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository(value = "memberRepository")
public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByClubIdAndUserId(Long clubId, Long userId);

    Member findByClubIdAndUserId(Long clubId, Long userId);

    Optional<Member> findByIdAndClubId(Long id, Long clubId);
}
