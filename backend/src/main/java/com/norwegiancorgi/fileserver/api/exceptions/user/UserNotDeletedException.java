package com.norwegiancorgi.fileserver.api.exceptions.user;

public class UserNotDeletedException extends Exception {
    private static final String ERROR_MESSAGE = "User not could not be deleted";
    public UserNotDeletedException(Throwable cause) {
        super(ERROR_MESSAGE, cause);
    }
}
