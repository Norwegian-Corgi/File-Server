package com.norwegiancorgi.fileserver.api.exceptions;

public class FileNotFoundException extends Exception {
    private static final String ERROR_MESSAGE = "File not found.";

    public FileNotFoundException() {
        super(ERROR_MESSAGE);
    }

    public FileNotFoundException(Throwable cause) {
        super(ERROR_MESSAGE, cause);
    }
}
