package com.norwegiancorgi.fileserver.internal.services;

import com.norwegiancorgi.fileserver.api.exceptions.FileNotDeletedException;
import com.norwegiancorgi.fileserver.api.exceptions.FileNotFoundException;
import com.norwegiancorgi.fileserver.api.exceptions.FileNotUploadedException;
import com.norwegiancorgi.fileserver.internal.entities.Filez;
import com.norwegiancorgi.fileserver.internal.repositories.FileRepository;
import com.norwegiancorgi.fileserver.internal.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    public List<Filez> getAllByUserEmail(String email) {
        return fileRepository.findAllByUserEmail(email);
    }

    public ResponseEntity<Resource> getResourceByFileId(UUID uuid) throws FileNotFoundException {
        final Filez file = getById(uuid);
        final Resource resource = getResource(file);
        updateFile(file);
        return resourceToResponseEntity(resource, file);
    }

    private Resource getResource(Filez file) throws FileNotFoundException {
        try {
            final Path path = Paths.get(file.getPath());
            final Resource resource = new UrlResource(path.toUri());
            if (resource.exists()) {
                return resource;
            }
            throw new FileNotFoundException();
        } catch (MalformedURLException e) {
            throw new FileNotFoundException(e);
        }
    }

    private ResponseEntity<Resource> resourceToResponseEntity(Resource resource, Filez file) throws FileNotFoundException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""))
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .body(resource);
    }

    private void updateFile(Filez file) {
        updateNumberOfDownloads(file);
        fileRepository.save(file);
    }

    private void updateNumberOfDownloads(Filez file) {
        final Integer numberOfDownloads = file.getNumberOfDownloads() + 1;
        file.setNumberOfDownloads(numberOfDownloads);
    }

    public void upload(String email, MultipartFile multipartFile) throws FileNotUploadedException {
        try {
            final Path path = getUploadPath(email, multipartFile);
            saveFileOnServer(multipartFile, path);
            persistInDatabase(multipartFile, path, email);
        } catch (IOException e) {
            throw new FileNotUploadedException(e);
        }
    }

    private Path getUploadPath(String email, MultipartFile multipartFile) throws IOException {
        final Path directory = FileUtil.getPath(email);
        final File file = new File(directory.toString());
        if (!file.exists()) {
            Files.createDirectories(directory);
        }
        return directory.resolve(Objects.requireNonNull(multipartFile.getOriginalFilename()));
    }

    private void saveFileOnServer(MultipartFile file, Path path) throws IOException {
        Files.copy(file.getInputStream(), path);
    }

    private void persistInDatabase(MultipartFile file, Path path, String email) {
        final Filez filez = new Filez(null,
                Objects.requireNonNull(file.getOriginalFilename()),
                Objects.requireNonNull(file.getContentType()),
                path.toAbsolutePath().toString(),
                0,
                email);
        fileRepository.save(filez);
    }

    public void delete(UUID uuid) throws FileNotDeletedException {
        try {
            final Filez filez = getById(uuid);
            deleteFileFromSystem(filez.getPath());
            fileRepository.deleteById(uuid);
        } catch (FileNotFoundException e) {
            throw new FileNotDeletedException(e);
        }
    }

    private Filez getById(UUID id) throws FileNotFoundException {
        return fileRepository.findById(id).orElseThrow(FileNotFoundException::new);
    }

    private void deleteFileFromSystem(String filePath) throws FileNotDeletedException {
        final File file = getFileFromPath(filePath);
        if (!file.delete()) {
            throw new FileNotDeletedException();
        }
    }

    private File getFileFromPath(String filePath) {
        final Path path = Paths.get(filePath);
        return new File(path.toString());
    }
}
