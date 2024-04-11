package com.norwegiancorgi.fileserver.api.exceptions;

public class FileNotDeletedException extends Exception {
    private static final String ERROR_MESSAGE = "File could not be deleted.";

    public FileNotDeletedException() {
        super(ERROR_MESSAGE);
    }

    public FileNotDeletedException(Throwable cause) {
        super(ERROR_MESSAGE, cause);
    }
}
