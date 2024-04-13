package com.norwegiancorgi.fileserver.internal.utils;

import java.nio.file.Path;
import java.nio.file.Paths;


public class FileUtil {
    private static final String ROOT_LOCATION = "FileServer/";

    public static Path getPath(String id) {
        final String location = ROOT_LOCATION.concat(id);
        return Paths.get(location);
    }
}
