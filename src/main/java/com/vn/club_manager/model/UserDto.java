package com.vn.club_manager.model;

import com.vn.club_manager.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private Date createdAt;
    private String code;
    private String email;
    private String phone;
    private String major;
    private Integer status;

    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.createdAt = user.getCreatedAt();
        this.code = user.getCode();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.major = user.getMajor();
        this.status = user.getStatus();
    }
}
