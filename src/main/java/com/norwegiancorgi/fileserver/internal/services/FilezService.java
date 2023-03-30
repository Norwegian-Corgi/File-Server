package com.norwegiancorgi.fileserver.internal.services;

import com.norwegiancorgi.fileserver.api.exceptions.filez.FileNotDeletedException;
import com.norwegiancorgi.fileserver.api.exceptions.filez.FileNotFoundException;
import com.norwegiancorgi.fileserver.api.exceptions.filez.FileNotUploadedException;
import com.norwegiancorgi.fileserver.api.exceptions.filez.FilesNotFetchedException;
import com.norwegiancorgi.fileserver.api.exceptions.user.UserNotFoundException;
import com.norwegiancorgi.fileserver.internal.entities.Filez;
import com.norwegiancorgi.fileserver.internal.entities.Userz;
import com.norwegiancorgi.fileserver.internal.repositories.IFilezRepository;
import com.norwegiancorgi.fileserver.internal.repositories.IUserRepository;
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
public class FilezService {

    private final IFilezRepository filezRepository;
    private final IUserRepository userRepository;

    public List<Filez> getAllFiles(UUID uuid) throws FilesNotFetchedException {
        final Userz userz;
        try {
            userz = this.getUser(uuid);
        } catch (UserNotFoundException e) {
            throw new FilesNotFetchedException(e);
        }
        return filezRepository.findAllByUserz(userz);
    }

    public ResponseEntity<Resource> getFile(UUID uuid) throws FileNotFoundException {
        final Filez file = filezRepository.findById(uuid).orElseThrow(FileNotFoundException::new);
        final Resource resource = this.getResource(file);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""))
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .body(resource);
    }

    private Resource getResource(Filez file) throws FileNotFoundException {
        final Path path = Paths.get(file.getPath());
        final Resource resource;
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            throw new FileNotFoundException(e);
        }
        final Integer numberOfDownloads = file.getNumberOfDownloads() + 1;
        file.setNumberOfDownloads(numberOfDownloads);
        if (resource.exists()) {
            filezRepository.save(file);
            return resource;
        } else {
            throw new FileNotFoundException();
        }
    }

    public void upload(UUID uuid, MultipartFile multipartFile) throws FileNotUploadedException {
        final Path path = FileUtil.getPath(uuid.toString()).resolve(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try {
            final Userz userz = this.getUser(uuid);
            Files.copy(multipartFile.getInputStream(), path);
            final Filez filez = new Filez(multipartFile.getOriginalFilename(),
                    Objects.requireNonNull(multipartFile.getContentType()),
                    path.toAbsolutePath().toString(),
                    0,
                    userz);
            filezRepository.save(filez);
        } catch (UserNotFoundException | IOException e) {
            throw new FileNotUploadedException(e);
        }
    }

    private Userz getUser(UUID uuid) throws UserNotFoundException {
        return userRepository.findById(uuid).orElseThrow(() -> new UserNotFoundException("User not found."));
    }

    public void delete(UUID uuid) throws FileNotDeletedException {
        final Filez filez = filezRepository.findById(uuid).orElseThrow(() -> new FileNotDeletedException(new FileNotFoundException()));
        final Path uploadPath = Paths.get(filez.getPath());
        final File file = new File(uploadPath.toString());
        if (file.delete()) {
            filezRepository.deleteById(uuid);
        } else {
            throw new FileNotDeletedException();
        }
    }
}
