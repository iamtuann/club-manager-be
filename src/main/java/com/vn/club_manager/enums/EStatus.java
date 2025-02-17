package com.vn.club_manager.enums;

import lombok.Getter;

@Getter
public enum EStatus {
    INACTIVE(0, "Inactive"),
    ACTIVE(1, "Active");

    private final Integer value;
    private final String title;

    EStatus(Integer value, String title) {
        this.value = value;
        this.title = title;
    }
}
