package com.vn.club_manager.repository;

import com.vn.club_manager.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "memberRepository")
public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByClubIdAndUserId(Long clubId, Long memberId);
}
