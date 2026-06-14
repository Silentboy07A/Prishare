package com.prishare.backend.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.nio.file.*;
import com.prishare.backend.model.FileRecord;
import com.prishare.backend.service.FileRecordService;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class FileController {

    private final Path uploadPath = Paths.get("uploads");

    @Autowired
    private FileRecordService fileRecordService;

 @PostMapping("/upload")
public String uploadFile(@RequestParam("file") MultipartFile file)
        throws IOException {

    if (!Files.exists(uploadPath)) {
        Files.createDirectories(uploadPath);
    }

    Path filePath = uploadPath.resolve(file.getOriginalFilename());

    Files.copy(
            file.getInputStream(),
            filePath,
            StandardCopyOption.REPLACE_EXISTING
    );

    FileRecord record = new FileRecord();

    record.setFilename(file.getOriginalFilename());
    record.setOwnerEmail("sakthi@gmail.com");

    fileRecordService.save(record);

    return "File uploaded successfully";
}

    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> downloadFile(
            @PathVariable String filename) throws IOException {

        Path filePath = uploadPath.resolve(filename);

        Resource resource = new UrlResource(filePath.toUri());

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + filename + "\""
                )
                .body(resource);
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
@PostMapping("/share/{filename}")
public String shareFile(@PathVariable String filename) {

    return "http://localhost:8081/download/" + filename;
}
}