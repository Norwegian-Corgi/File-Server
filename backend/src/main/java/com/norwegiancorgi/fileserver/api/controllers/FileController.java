package com.norwegiancorgi.fileserver.api.controllers;

import com.norwegiancorgi.fileserver.api.exceptions.FileNotDeletedException;
import com.norwegiancorgi.fileserver.api.exceptions.FileNotFoundException;
import com.norwegiancorgi.fileserver.api.exceptions.FileNotUploadedException;
import com.norwegiancorgi.fileserver.internal.entities.Filez;
import com.norwegiancorgi.fileserver.internal.services.FileService;
import com.norwegiancorgi.fileserver.internal.services.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;
    private final TokenService tokenService;

    @GetMapping
    public ResponseEntity<List<Filez>> getAllFiles(JwtAuthenticationToken token) {
        final String email = tokenService.getEmailFromToken(token);
        return ResponseEntity.ok(fileService.getAllByUserEmail(email));
    }

    @GetMapping("/download/{uuid}")
    public ResponseEntity<Resource> getResourceByFileId(@PathVariable("uuid") UUID uuid) throws FileNotFoundException {
        return fileService.getResourceByFileId(uuid);
    }

    @PostMapping
    public ResponseEntity<String> upload(@RequestBody MultipartFile file, JwtAuthenticationToken token) throws FileNotUploadedException {
        final String email = tokenService.getEmailFromToken(token);
        fileService.upload(email, file);
        return ResponseEntity.ok("File uploaded successfully");
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<String> delete(@PathVariable("uuid") UUID uuid) throws FileNotDeletedException {
        fileService.delete(uuid);
        return ResponseEntity.ok("File deleted successfully");
    }
}
