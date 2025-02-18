package com.vn.club_manager.enums;

import lombok.Getter;

@Getter
public enum ERole {
    MANAGER("MANAGER"),
    USER("USER");

    private final String name;

    ERole(String name) {
        this.name = name;
    }
}
