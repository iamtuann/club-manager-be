package com.vn.club_manager.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role")
public class Role {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 45)
    @Column(name = "name", length = 45)
    private String name;

    @Size(max = 255)
    @Column(name = "display_name")
    private String displayName;

}