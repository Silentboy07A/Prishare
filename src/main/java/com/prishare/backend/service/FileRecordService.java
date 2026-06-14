package com.prishare.backend.service;

import com.prishare.backend.model.FileRecord;
import com.prishare.backend.repository.FileRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileRecordService {

    @Autowired
    private FileRecordRepository repository;

    public FileRecord save(FileRecord record) {
        return repository.save(record);
    }
    public FileRecord findByShareId(String shareId) {
    return repository.findByShareId(shareId).orElse(null);
}
}