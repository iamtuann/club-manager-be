package com.vn.club_manager.controller;

import com.vn.club_manager.model.ClubDto;
import com.vn.club_manager.model.PageDto;
import com.vn.club_manager.model.UserDto;
import com.vn.club_manager.service.UserService;
import com.vn.club_manager.utils.PageUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final PageUtil pageUtil;

    @GetMapping("")
    private ResponseEntity<PageDto<UserDto>> searchUsers(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(value = "pageIndex", defaultValue = "1") int pageIndex,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "orderBy", required = false) String orderBy
    ) {
        Pageable pageable = pageUtil.getPageable(pageIndex, pageSize, key, orderBy);
        PageDto<UserDto> clubs = userService.searchUsers(name, pageable);
        return ResponseEntity.ok(clubs);
    }

    @GetMapping("{id}")
    private ResponseEntity<UserDto> getUser(@PathVariable long id) {
        UserDto user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("{id}")
    private ResponseEntity<?> deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("Delete user successfully.");
    }

    @GetMapping("/not-in-club/{clubId}")
    public ResponseEntity<List<UserDto>> getUsersNotInClub(@PathVariable Long clubId) {
        List<UserDto> users = userService.getUsersNotInClub(clubId);
        return ResponseEntity.ok(users);
    }
}
