package com.example.control_work_8.service;

import com.example.control_work_8.dto.FileInfoDto;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileInfoService {
    void uploadFile(MultipartFile file, Boolean isPublic, String email) throws IOException;

    Page<FileInfoDto> getFilesByUser(String email, int page, int size);

    Page<FileInfoDto> getPublicFiles(int page, int size);

    FileInfoDto getPublicFileById(Long id);

    FileInfoDto getPrivateFileByKey(String shareKey);

    void addDownloadCount(Long id);

    void deleteShareKey(Long id);
}
