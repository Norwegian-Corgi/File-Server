package com.norwegiancorgi.fileserver.api.controllers;

import com.norwegiancorgi.fileserver.api.exceptions.filez.FileNotDeletedException;
import com.norwegiancorgi.fileserver.api.exceptions.filez.FilesNotFetchedException;
import com.norwegiancorgi.fileserver.api.exceptions.filez.FileNotFoundException;
import com.norwegiancorgi.fileserver.api.exceptions.filez.FileNotUploadedException;
import com.norwegiancorgi.fileserver.internal.entities.Filez;
import com.norwegiancorgi.fileserver.internal.services.FilezService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/filez")
@RequiredArgsConstructor
public class FilezController {

    private final FilezService filezService;

    @GetMapping("/{uuid}")
    public ResponseEntity<List<Filez>> getAllFiles(@PathVariable UUID uuid) throws FilesNotFetchedException {
        return ResponseEntity.ok(filezService.getAllFiles(uuid));
    }

    @GetMapping("/download/{uuid}")
    public ResponseEntity<Resource> download(@PathVariable("uuid") UUID uuid) throws FileNotFoundException {
        return filezService.getFile(uuid);
    }

    @PostMapping
    public ResponseEntity<String> upload(@RequestParam UUID uuid, @RequestParam MultipartFile file) throws FileNotUploadedException {
        filezService.upload(uuid, file);
        return ResponseEntity.ok("File uploaded successfully");
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<String> delete(@PathVariable("uuid") UUID uuid) throws FileNotDeletedException {
        filezService.delete(uuid);
        return ResponseEntity.ok("File deleted successfully");
    }
}
