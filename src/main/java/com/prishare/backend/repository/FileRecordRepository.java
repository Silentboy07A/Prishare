package com.prishare.backend.repository;

import com.prishare.backend.model.FileRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public interface FileRecordRepository
        extends JpaRepository<FileRecord, Long> {

    List<FileRecord> findByOwnerEmail(String ownerEmail);

    Optional<FileRecord> findByShareId(String shareId);
}