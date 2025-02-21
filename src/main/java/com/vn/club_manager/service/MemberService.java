package com.vn.club_manager.service;

import com.vn.club_manager.model.MemberDto;
import com.vn.club_manager.model.PageDto;
import com.vn.club_manager.model.request.AddMemberRequest;
import org.springframework.data.domain.Pageable;

public interface MemberService {

    PageDto<MemberDto> getMembers(long clubId, String name, Pageable pageable);

    MemberDto getMember(Long memberId, Long clubId, Long userId);

    void AddMember(Long clubId, AddMemberRequest request);

    void deleteMember(Long clubId, Long memId, Long userId);
}
