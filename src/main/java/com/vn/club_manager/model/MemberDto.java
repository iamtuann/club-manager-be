package com.vn.club_manager.model;

import com.vn.club_manager.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    private Long id;
    private UserDto user;
    private Long clubId;
    private Date joinDate;
    private Integer status;

    public MemberDto(Member member) {
        this.id = member.getId();
        this.user = new UserDto(member.getUser());
        this.clubId = member.getClub().getId();
        this.joinDate = member.getJoinDate();
        this.status = member.getStatus();
    }
}
