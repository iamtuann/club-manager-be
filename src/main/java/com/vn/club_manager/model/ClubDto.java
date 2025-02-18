package com.vn.club_manager.model;

import com.vn.club_manager.entity.Club;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClubDto {
    private Long id;
    private String name;
    private String description;
    private Date createdAt;
    private Date dissolvedAt;
    private UserDto manager;
    private Integer memberCount;
    private Integer status;

    public ClubDto(Club club) {
        this.id = club.getId();
        this.name = club.getName();
        this.description = club.getDescription();
        this.createdAt = club.getCreatedAt();
        this.dissolvedAt = club.getDissolvedAt();
        this.status = club.getStatus();
        this.memberCount = club.getMembers() != null ? club.getMembers().size() : 0;
        this.manager = club.getManager() != null ? new UserDto(club.getManager()) : null;
    }
}
