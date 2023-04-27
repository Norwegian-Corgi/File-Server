package com.norwegiancorgi.fileserver.api.exceptions.filez;

public class FilesNotFetchedException extends Exception {
    private static final String ERROR_MESSAGE = "Files could not be fetched.";

    public FilesNotFetchedException(Throwable cause) {
        super(ERROR_MESSAGE, cause);
    }
}
