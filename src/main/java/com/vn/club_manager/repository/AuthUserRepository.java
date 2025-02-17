package com.vn.club_manager.repository;

import com.vn.club_manager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository(value = "authUserRepository")
public interface AuthUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    User findAuthUserById(long id);

    Boolean existsByEmail(String email);
}
