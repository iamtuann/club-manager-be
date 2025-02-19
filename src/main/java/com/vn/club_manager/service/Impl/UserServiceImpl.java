package com.vn.club_manager.service.Impl;

import com.vn.club_manager.entity.User;
import com.vn.club_manager.model.UserDto;
import com.vn.club_manager.repository.AuthUserRepository;
import com.vn.club_manager.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    AuthUserRepository authUserRepository;

    @Override
    public List<UserDto> getUsersNotInClub(Long clubId) {
        List<User> users = authUserRepository.findUsersNotInClub(clubId);
        return users.stream().map(UserDto::new).toList();
    }
}
