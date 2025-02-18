package com.vn.club_manager.service;

import com.vn.club_manager.model.BoardDto;
import com.vn.club_manager.model.ClubDto;
import com.vn.club_manager.model.MemberDto;
import com.vn.club_manager.model.PageDto;
import com.vn.club_manager.model.request.BoardRequest;
import com.vn.club_manager.model.request.ClubRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClubService {

    ClubDto findClubById(Long id);

    PageDto<ClubDto> searchClubs(String name, Pageable pageable);

    ClubDto create(ClubRequest request);

    ClubDto update(long id, ClubRequest request);

    void setPresident(long id, Long userId);

    void delete(long id);
}
