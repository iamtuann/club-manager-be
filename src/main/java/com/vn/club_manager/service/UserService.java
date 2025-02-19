package com.vn.club_manager.service;

import com.vn.club_manager.model.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getUsersNotInClub(Long clubId);
}
