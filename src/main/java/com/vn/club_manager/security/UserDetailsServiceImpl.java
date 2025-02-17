package com.vn.club_manager.security;

import com.vn.club_manager.entity.User;
import com.vn.club_manager.repository.AuthUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private AuthUserRepository authUserRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = authUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email: "+ username));
        return UserDetailsImpl.build(user);
    }
}
