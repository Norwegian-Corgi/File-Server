package com.norwegiancorgi.fileserver.api.exceptions.user;

public class UserNotCreatedException extends Exception {

    public UserNotCreatedException(String message) {
        super(message);
    }

    public UserNotCreatedException(String message, Throwable cause) {
        super(message, cause);
    }
}
