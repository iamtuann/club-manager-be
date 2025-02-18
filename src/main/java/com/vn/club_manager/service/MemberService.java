package com.vn.club_manager.service;

import com.vn.club_manager.model.request.AddMemberRequest;

public interface MemberService {
    void AddMember(Long clubId, AddMemberRequest request);
}
