package com.vn.club_manager.controller;

import com.vn.club_manager.model.UserDto;
import com.vn.club_manager.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/not-in-club/{clubId}")
    public ResponseEntity<List<UserDto>> getUsersNotInClub(@PathVariable Long clubId) {
        List<UserDto> users = userService.getUsersNotInClub(clubId);
        return ResponseEntity.ok(users);
    }
}
