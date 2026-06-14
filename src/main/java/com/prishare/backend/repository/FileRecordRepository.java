package com.prishare.backend.repository;

import com.prishare.backend.model.FileRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRecordRepository
        extends JpaRepository<FileRecord, Long> {

    List<FileRecord> findByOwnerEmail(String ownerEmail);
}