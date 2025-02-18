package com.vn.club_manager.repository;

import com.vn.club_manager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository(value = "authUserRepository")
public interface AuthUserRepository extends JpaRepository<User, Long> {

    @Query("SELECT COUNT(u) > 0 FROM User u " +
            "JOIN u.roles r " +
            "WHERE u.id = :userId AND r.name = 'MANAGER'")
    boolean isManager(@Param("userId") Long userId);

    Optional<User> findByUsername(String username);

    User findAuthUserById(long id);

    boolean existsByUsername(String username);
}
