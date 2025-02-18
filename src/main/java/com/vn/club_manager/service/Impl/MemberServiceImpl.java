package com.vn.club_manager.service.Impl;

import com.vn.club_manager.entity.Club;
import com.vn.club_manager.entity.Member;
import com.vn.club_manager.entity.User;
import com.vn.club_manager.enums.EStatus;
import com.vn.club_manager.exception.ResourceNotFoundException;
import com.vn.club_manager.model.request.AddMemberRequest;
import com.vn.club_manager.repository.AuthUserRepository;
import com.vn.club_manager.repository.ClubRepository;
import com.vn.club_manager.repository.MemberRepository;
import com.vn.club_manager.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;
    private final AuthUserRepository authUserRepository;

    @Override
    public void AddMember(Long clubId, AddMemberRequest request) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new ResourceNotFoundException("Club", "id", clubId));

        for (Long userId : request.getUserIds()) {
            User user = authUserRepository.findById(userId).orElse(null);
            if (user == null) continue;
            Member member = new Member();
            member.setClub(club);
            member.setUser(user);
            member.setJoinDate(new Date());
            member.setStatus(EStatus.ACTIVE.getValue());
            memberRepository.save(member);
        }
    }
}
