package com.vn.club_manager.service;

import com.vn.club_manager.model.ClubDto;
import com.vn.club_manager.model.PageDto;
import com.vn.club_manager.model.request.ClubRequest;
import org.springframework.data.domain.Pageable;

public interface ClubService {

    ClubDto findClubById(Long id);

    String getUserRoleInClub(Long clubId, Long userId);

    PageDto<ClubDto> searchClubs(String name, Pageable pageable, Long userId);

    ClubDto create(ClubRequest request);

    ClubDto update(long id, ClubRequest request);

    void setPresident(long id, Long userId);

    void delete(long id);
}
