package com.vn.club_manager.service.Impl;

import com.vn.club_manager.entity.Role;
import com.vn.club_manager.entity.User;
import com.vn.club_manager.enums.EStatus;
import com.vn.club_manager.exception.APIException;
import com.vn.club_manager.exception.ResourceNotFoundException;
import com.vn.club_manager.model.AuthUserResponse;
import com.vn.club_manager.model.request.LoginRequest;
import com.vn.club_manager.model.request.RegisterRequest;
import com.vn.club_manager.repository.AuthUserRepository;
import com.vn.club_manager.repository.ClubRepository;
import com.vn.club_manager.repository.RoleRepository;
import com.vn.club_manager.security.JwtTokenProvider;
import com.vn.club_manager.security.UserDetailsImpl;
import com.vn.club_manager.service.AuthUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthUserServiceImpl implements AuthUserService {
    private final AuthUserRepository authUserRepository;
    private final RoleRepository roleRepository;
    private final ClubRepository clubRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean isManager(Long userId) {
        return authUserRepository.isManager(userId);
    }

    @Override
    public boolean isClubManager(Long userId, Long clubId) {
        return clubRepository.existsByIdAndManagerId(userId, clubId);
    }

    @Override
    public boolean hasClubManagementRights(Long userId, Long clubId) {
        return isManager(userId) || isClubManager(userId, clubId);
    }

    @Override
    public AuthUserResponse login(LoginRequest request) {
        if (!authUserRepository.existsByUsername(request.getUsername())) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Username is not registered");
        }
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtTokenProvider.generateToken(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            User user = authUserRepository.findAuthUserById(userDetails.getId());
            return new AuthUserResponse(authentication, token, user.getRoles());
        } catch (BadCredentialsException ex) {
            throw new APIException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }
    }

    @Override
    public User register(RegisterRequest request) {
        if (authUserRepository.existsByUsername(request.getUsername())) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Username is already exists!");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setCode(request.getCode());
        user.setPhone(request.getPhone());
        user.setMajor(request.getMajor());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setStatus(EStatus.ACTIVE.getValue());
        Set<Role> roles = new HashSet<>();
        for (Long id : request.getRoleIds()) {
            roles.add(roleRepository.findRoleById(id));
        }
        user.setRoles(roles);
        return authUserRepository.save(user);
    }

    @Override
    public void update(long id, RegisterRequest request) {
        User user = authUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        if (authUserRepository.existsByUsername(request.getUsername())) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Username is already exists!");
        }
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setCode(request.getCode());
        user.setPhone(request.getPhone());
        user.setMajor(request.getMajor());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        Set<Role> roles = new HashSet<>();
        for (Long roleId : request.getRoleIds()) {
            roles.add(roleRepository.findRoleById(roleId));
        }
        user.setRoles(roles);
        authUserRepository.save(user);
    }
}
