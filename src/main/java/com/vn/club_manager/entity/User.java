package com.vn.club_manager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 255)
    @Column(name = "username")
    private String username;

    @Size(max = 255)
    @Column(name = "password")
    private String password;

    @Size(max = 45)
    @Column(name = "first_name", length = 45)
    private String firstName;

    @Size(max = 45)
    @Column(name = "last_name", length = 45)
    private String lastName;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Size(max = 45)
    @Column(name = "code", length = 45)
    private String code;

    @Size(max = 255)
    @Column(name = "email")
    private String email;

    @Size(max = 12)
    @Column(name = "phone", length = 12)
    private String phone;

    @Size(max = 255)
    @Column(name = "major")
    private String major;

    @Column(name = "status")
    private Integer status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = new Date();
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }
}