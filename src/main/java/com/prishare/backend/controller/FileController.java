package com.prishare.backend.controller;

import com.prishare.backend.model.FileRecord;
import com.prishare.backend.security.FileEncryptionUtil;
import com.prishare.backend.service.FileRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@RestController
public class FileController {

private final Path uploadPath = Paths.get("uploads");

@Autowired
private FileRecordService fileRecordService;

@PostMapping("/upload")
public String uploadFile(@RequestParam("file") MultipartFile file)
        throws Exception {

    if (!Files.exists(uploadPath)) {
        Files.createDirectories(uploadPath);
    }

    Path filePath = uploadPath.resolve(file.getOriginalFilename());

    byte[] fileBytes = file.getBytes();

    byte[] encryptedBytes =
            FileEncryptionUtil.encrypt(fileBytes);

    Files.write(filePath, encryptedBytes);

    FileRecord record = new FileRecord();
    record.setFilename(file.getOriginalFilename());
    record.setOwnerEmail("sakthi@gmail.com");
    record.setShareId(UUID.randomUUID().toString());

    fileRecordService.save(record);

    return "File uploaded successfully";
}

@GetMapping("/download/{filename}")
public ResponseEntity<Resource> downloadFile(
        @PathVariable String filename) throws Exception {

    Path filePath = uploadPath.resolve(filename);

    byte[] encryptedBytes =
            Files.readAllBytes(filePath);

    byte[] decryptedBytes =
            FileEncryptionUtil.decrypt(encryptedBytes);

    return ResponseEntity.ok()
            .header(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + filename + "\""
            )
            .body(new ByteArrayResource(decryptedBytes));
}

@GetMapping("/files")
public String[] listFiles() {
    java.io.File folder = new java.io.File("uploads");
    return folder.list();
}

@DeleteMapping("/delete/{filename}")
public String deleteFile(@PathVariable String filename)
        throws IOException {

    Path path = uploadPath.resolve(filename);

    if (Files.deleteIfExists(path)) {
        return "File deleted successfully";
    }

    return "File not found";
}

@GetMapping("/shared/{shareId}")
public ResponseEntity<Resource> downloadSharedFile(
        @PathVariable String shareId) throws IOException {

    FileRecord record =
            fileRecordService.findByShareId(shareId);

    if (record == null) {
        return ResponseEntity.notFound().build();
    }

    Path filePath =
            uploadPath.resolve(record.getFilename());

    Resource resource =
            new UrlResource(filePath.toUri());

    return ResponseEntity.ok()
            .header(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" +
                            record.getFilename() + "\""
            )
            .body(resource);
}

@PostMapping("/share/{shareId}")
public String shareFile(@PathVariable String shareId) {
    return "http://localhost:8081/shared/" + shareId;
}


}
