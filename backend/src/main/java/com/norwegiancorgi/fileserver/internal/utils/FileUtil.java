package com.norwegiancorgi.fileserver.internal.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class FileUtil {
    private static final String ROOT_LOCATION = "/File Server/";

    public static void createFolder(String id) throws IOException {
        final Path location = getPath(id);
        Files.createDirectories(location);
    }

    public static void deleteFiles(String id) throws IOException {
        final Path dir = getPath(id); //path to the directory
        final List<Path> paths = Files
                .walk(dir) // Traverse the file tree in depth-first order
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        for (Path path : paths) {
            Files.delete(path);
        }
    }

    public static Path getPath(String id) {
        final String location = ROOT_LOCATION.concat(id);
        return Paths.get(location);
    }
}
