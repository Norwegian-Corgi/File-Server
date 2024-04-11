package com.norwegiancorgi.fileserver.api.exceptions;

public class FileNotUploadedException extends Exception {
    private static final String ERROR_MESSAGE = "File could not be uploaded.";

    public FileNotUploadedException(Throwable cause) {
        super(ERROR_MESSAGE, cause);
    }
}
