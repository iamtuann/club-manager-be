package com.vn.club_manager.service;

import com.vn.club_manager.model.PageDto;
import com.vn.club_manager.model.UserDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    PageDto<UserDto> searchUsers(String name, Pageable pageable);

    UserDto getUserById(Long id);

    void deleteUser(Long id);

    List<UserDto> getUsersNotInClub(Long clubId);
}
