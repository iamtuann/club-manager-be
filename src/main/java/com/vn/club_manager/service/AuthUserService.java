package com.vn.club_manager.service;

import com.vn.club_manager.entity.User;
import com.vn.club_manager.model.AuthUserResponse;
import com.vn.club_manager.model.request.LoginRequest;
import com.vn.club_manager.model.request.RegisterRequest;

public interface AuthUserService {
    AuthUserResponse login(LoginRequest request);

    User register(RegisterRequest request);

    void update(long id, RegisterRequest request);
}
