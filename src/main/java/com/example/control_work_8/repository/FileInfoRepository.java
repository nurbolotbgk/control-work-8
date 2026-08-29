package com.example.control_work_8.repository;

import com.example.control_work_8.model.FileInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileInfoRepository extends JpaRepository<FileInfo, Long> {
    Page<FileInfo> findByUserId(Long userId, Pageable pageable);
    Page<FileInfo> findIsPublicTrue(Pageable pageable);
    Optional<FileInfo> findByShareKey(String shareKey);

}
