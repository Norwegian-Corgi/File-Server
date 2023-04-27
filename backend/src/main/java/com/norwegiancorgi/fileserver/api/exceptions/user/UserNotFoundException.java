package com.norwegiancorgi.fileserver.api.exceptions.user;

public class UserNotFoundException extends Exception {

    public UserNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
