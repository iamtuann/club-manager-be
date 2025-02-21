package com.vn.club_manager.service.Impl;

import com.vn.club_manager.entity.Club;
import com.vn.club_manager.entity.Member;
import com.vn.club_manager.entity.User;
import com.vn.club_manager.enums.EStatus;
import com.vn.club_manager.exception.NoPermissionException;
import com.vn.club_manager.exception.ResourceNotFoundException;
import com.vn.club_manager.model.MemberDto;
import com.vn.club_manager.model.PageDto;
import com.vn.club_manager.model.request.AddMemberRequest;
import com.vn.club_manager.repository.AuthUserRepository;
import com.vn.club_manager.repository.ClubRepository;
import com.vn.club_manager.repository.MemberRepository;
import com.vn.club_manager.service.AuthUserService;
import com.vn.club_manager.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;
    private final AuthUserRepository authUserRepository;
    private final AuthUserService authUserService;


    @Override
    public PageDto<MemberDto> getMembers(long clubId, String name, Pageable pageable) {
        Page<Member> pages = clubRepository.getMembersByClubId(clubId, name, pageable);
        List<MemberDto> members = pages.stream().map(MemberDto::new).toList();
        return new PageDto<>(members, pages);
    }

    @Override
    public MemberDto getMember(Long memberId, Long clubId, Long userId) {
        if (authUserService.hasClubManagementRights(userId, clubId)) {
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new ResourceNotFoundException("Member", "id", memberId));
            return new MemberDto(member);
        } else {
            throw new NoPermissionException("get member info");
        }
    }

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

    @Override
    public void deleteMember(Long clubId, Long memId, Long userId) {
        if (!authUserService.hasClubManagementRights(userId, clubId)) {
            throw new NoPermissionException("delete this board");
        }
        Member member = memberRepository.findByIdAndClubId(memId, clubId)
                .orElseThrow(() -> new ResourceNotFoundException("Member", "id", memId));
        memberRepository.delete(member);
    }
}
