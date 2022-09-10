package com.norwegiancorgi.fileserver.services;

import com.norwegiancorgi.fileserver.models.Filez;
import com.norwegiancorgi.fileserver.models.Owner;
import com.norwegiancorgi.fileserver.repositories.FilezRepository;
import com.norwegiancorgi.fileserver.repositories.OwnerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class FilezService {

    private FilezRepository filezRepository;
    private OwnerRepository ownerRepository;
    private final Logger logger = LoggerFactory.getLogger(FilezService.class);
    @Value("${root.location}")
    private String rootLocation;

    /**
     * Constructor
     */
    @Autowired
    public FilezService(FilezRepository filezRepository, OwnerRepository ownerRepository) {
        this.filezRepository = filezRepository;
        this.ownerRepository = ownerRepository;
    }

    public List<Filez> getAllFiles(Long ownerId) throws Exception {
        try {
            final List<Filez> files = filezRepository.findByOwner(ownerId);
            logger.info("Files fetched successfully");
            return files;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    public Resource getResource(Long id, Long ownerId) throws Exception {
        final String currentUserFolder = rootLocation + ownerId.toString();
        final String name = filezRepository.getById(id).getName();
        final Path path = Paths.get(currentUserFolder).resolve(name);
        final Resource resource = new UrlResource(path.toUri());
        final Integer numberOfDownloads = filezRepository.getById(id).getNumberOfDownloads() + 1;
        if (resource.exists()) {
            filezRepository.updateNumberOfDownloads(id, numberOfDownloads);
            return resource;
        } else {
            logger.error("File could not be downloaded");
            throw new Exception("Could not download the file: " + name);
        }
    }

    public String getContentType(Resource resource, HttpServletRequest httpServletRequest) throws Exception {
        String contentType = null;
        try {
            contentType = httpServletRequest.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (Exception e) {
            throw new Exception("Could not determine file type!!!");
        }
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return contentType;
    }

    public String getFileName(Long id) {
        return filezRepository.getById(id).getName();
    }

    public void upload(MultipartFile multipartFile, Long ownerId) throws Exception {
        final String currentUserFolder = rootLocation + ownerId.toString();
        try {
            final Path path = Paths.get(currentUserFolder);
            final File file = new File(multipartFile.getOriginalFilename());
            Files.copy(multipartFile.getInputStream(), path.resolve(multipartFile.getOriginalFilename()));
            final Owner owner = ownerRepository.findById(ownerId).get();
            final Filez filez = new Filez(multipartFile.getOriginalFilename(),
                    multipartFile.getContentType(),
                    file.getAbsolutePath().replace(multipartFile.getOriginalFilename(), ""),
                    0,
                    multipartFile.getSize(),
                    owner);
            filezRepository.save(filez);
            logger.info("File successfully uploaded");
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    public void delete(Long id, Long ownerId) throws Exception {
        final String currentUserFolder = rootLocation + ownerId.toString();
        final Filez filez = filezRepository.getById(id);
        final Path uploadPath = Paths.get(currentUserFolder);
        final String filename = filez.getName();
        final File file = new File(String.valueOf(uploadPath.resolve(filename)));
        if (file.delete()) {
            filezRepository.deleteById(id);
            if (filezRepository.findById(id).isPresent()) {
                throw new Exception("File could not be deleted!!!");
            }
            logger.info("File successfully deleted");
        } else {
            throw new Exception("File could not be deleted!!!");
        }
    }
}
