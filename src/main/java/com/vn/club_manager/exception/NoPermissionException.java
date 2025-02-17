package com.vn.club_manager.exception;

public class NoPermissionException extends RuntimeException {
    public NoPermissionException(String message) {
        super("Do not have permission to " + message);
    }
}
