package com.vn.club_manager.model;

import com.vn.club_manager.entity.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventDetail {
    private Long id;
    private String name;
    private String description;
    private Date eventDate;
    private String address;
    private Date createdAt;
    private Long clubId;
    private String clubName;
    private Integer memberCount;
    private List<UserDto> users;

    public EventDetail(Event event) {
        this.id = event.getId();
        this.name = event.getName();
        this.description = event.getDescription();
        this.eventDate = event.getEventDate();
        this.address = event.getAddress();
        this.createdAt = event.getCreatedAt();
        this.clubId = event.getClub().getId();
        this.clubName = event.getClub().getName();
        this.memberCount = event.getUsers() != null ? event.getUsers().size() : 0;
        this.users = event.getUsers().stream().map(UserDto::new).toList();
    }
}
