package com.norwegiancorgi.fileserver.api.exceptions.user;

public class UserNotUpdatedException extends Exception {

    private static final String ERROR_MESSAGE = "User could not be updated.";
    public UserNotUpdatedException(Throwable cause) {
        super(ERROR_MESSAGE, cause);
    }
}
