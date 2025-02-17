package com.vn.club_manager.controller;

import com.vn.club_manager.model.AuthUserResponse;
import com.vn.club_manager.model.request.LoginRequest;
import com.vn.club_manager.model.request.RegisterRequest;
import com.vn.club_manager.service.AuthUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthUserController {
    private final AuthUserService authUserService;

    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<AuthUserResponse> login(@RequestBody LoginRequest request) {
        AuthUserResponse userResponse = authUserService.login(request);
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        authUserService.register(request);
        return new ResponseEntity<>("User registered successfully!", HttpStatus.CREATED);
    }
}
