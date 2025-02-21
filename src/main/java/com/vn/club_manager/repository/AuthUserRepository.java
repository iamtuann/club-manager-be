package com.vn.club_manager.repository;

import com.vn.club_manager.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    @Query("SELECT u FROM User u LEFT JOIN Member cm ON u.id = cm.user.id " +
            "AND cm.club.id = :clubId WHERE cm.id IS NULL")
    List<User> findUsersNotInClub(@Param("clubId") Long clubId);

    @Query(value = "SELECT u FROM User u " +
            "WHERE (:name IS NULL OR :name = '' " +
            "OR LOWER(u.firstName) LIKE LOWER(CONCAT('%', :name, '%')) " +
            "OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :name, '%')) " +
            "OR LOWER(CONCAT(u.firstName, ' ', u.lastName)) LIKE LOWER(CONCAT('%', :name, '%')) " +
            "OR u.username LIKE CONCAT('%', :name, '%')) ")
    Page<User> searchUser(String name, Pageable pageable);
}
