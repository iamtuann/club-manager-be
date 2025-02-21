package com.vn.club_manager.service.Impl;

import com.vn.club_manager.entity.User;
import com.vn.club_manager.exception.ResourceNotFoundException;
import com.vn.club_manager.model.PageDto;
import com.vn.club_manager.model.UserDto;
import com.vn.club_manager.repository.AuthUserRepository;
import com.vn.club_manager.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    AuthUserRepository authUserRepository;

    @Override
    public PageDto<UserDto> searchUsers(String name, Pageable pageable) {
        Page<User> pages = authUserRepository.searchUser(name, pageable);
        List<UserDto> users = pages.getContent().stream().map(UserDto::new).toList();
        return new PageDto<>(users, pages);
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = authUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return new UserDto(user);
    }

    @Override
    public void deleteUser(Long id) {
        User user = authUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        authUserRepository.deleteById(id);
    }

    @Override
    public List<UserDto> getUsersNotInClub(Long clubId) {
        List<User> users = authUserRepository.findUsersNotInClub(clubId);
        return users.stream().map(UserDto::new).toList();
    }
}
