package com.example.control_work_8.service.impl;

import com.example.control_work_8.dto.FileInfoDto;
import com.example.control_work_8.model.FileInfo;
import com.example.control_work_8.model.User;
import com.example.control_work_8.repository.FileInfoRepository;
import com.example.control_work_8.repository.UserRepository;
import com.example.control_work_8.service.FileInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileInfoServiceImpl implements FileInfoService {

    private final FileInfoRepository fileInfoRepository;
    private final UserRepository userRepository;

    private final String uploadDir = "uploads";


    @Override
    public void uploadFile(MultipartFile file, Boolean isPublic, String email) throws IOException {

        User user = userRepository.findByEmail(email).orElseThrow();

        Files.createDirectories(Paths.get(uploadDir));

        String originalName = file.getOriginalFilename();

        String storedName = UUID.randomUUID() + "_" + originalName;

        Path path = Paths.get(uploadDir, storedName);

        Files.write(path, file.getBytes());

        FileInfo fileInfo = new FileInfo();

        fileInfo.setOriginalName(originalName);
        fileInfo.setStoredName(storedName);
        fileInfo.setContentType(file.getContentType());
        fileInfo.setIsPublic(isPublic);
        fileInfo.setDownloadCount(0);
        fileInfo.setUser(user);

        if (Boolean.FALSE.equals(isPublic)) {
            fileInfo.setShareKey(UUID.randomUUID().toString());
        }

        fileInfoRepository.save(fileInfo);
    }


    @Override
    public Page<FileInfoDto> getFilesByUser(String email, int page, int size) {

        User user = userRepository.findByEmail(email).orElseThrow();

        Pageable pageable = PageRequest.of(page, size);

        Page<FileInfo> files = fileInfoRepository.findByUserId(user.getId(), pageable);

        return files.map(f -> FileInfoDto.builder()
                .id(f.getId())
                .originalName(f.getOriginalName())
                .storedName(f.getStoredName())
                .contentType(f.getContentType())
                .isPublic(f.getIsPublic())
                .shareKey(f.getShareKey())
                .downloadCount(f.getDownloadCount())
                .userId(f.getUser().getId())
                .build());
    }


    @Override
    public Page<FileInfoDto> getPublicFiles(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<FileInfo> files = fileInfoRepository.findByIsPublicTrue(pageable);

        return files.map(f -> FileInfoDto.builder()
                .id(f.getId())
                .originalName(f.getOriginalName())
                .storedName(f.getStoredName())
                .contentType(f.getContentType())
                .isPublic(f.getIsPublic())
                .shareKey(f.getShareKey())
                .downloadCount(f.getDownloadCount())
                .userId(f.getUser().getId())
                .build());
    }


    @Override
    public FileInfoDto getPublicFileById(Long id) {

        FileInfo fileInfo = fileInfoRepository.findById(id).orElseThrow();

        if (!Boolean.TRUE.equals(fileInfo.getIsPublic())) {
            throw new IllegalArgumentException("Файл является приватным");
        }

        return FileInfoDto.builder()
                .id(fileInfo.getId())
                .originalName(fileInfo.getOriginalName())
                .storedName(fileInfo.getStoredName())
                .contentType(fileInfo.getContentType())
                .isPublic(fileInfo.getIsPublic())
                .shareKey(fileInfo.getShareKey())
                .downloadCount(fileInfo.getDownloadCount())
                .userId(fileInfo.getUser().getId())
                .build();
    }


    @Override
    public FileInfoDto getPrivateFileByKey(String shareKey) {

        FileInfo fileInfo = fileInfoRepository.findByShareKey(shareKey).orElseThrow();

        if (Boolean.TRUE.equals(fileInfo.getIsPublic())) {
            throw new IllegalArgumentException("Файл является публичным");
        }

        return FileInfoDto.builder()
                .id(fileInfo.getId())
                .originalName(fileInfo.getOriginalName())
                .storedName(fileInfo.getStoredName())
                .contentType(fileInfo.getContentType())
                .isPublic(fileInfo.getIsPublic())
                .shareKey(fileInfo.getShareKey())
                .downloadCount(fileInfo.getDownloadCount())
                .userId(fileInfo.getUser().getId())
                .build();
    }


    @Override
    public void incrementDownloadCount(Long id) {

        FileInfo fileInfo = fileInfoRepository.findById(id).orElseThrow();

        fileInfo.setDownloadCount(fileInfo.getDownloadCount() + 1);

        fileInfoRepository.save(fileInfo);
    }


    @Override
    public void deleteShareKey(Long id) {

        FileInfo fileInfo = fileInfoRepository.findById(id).orElseThrow();

        fileInfo.setShareKey(null);

        fileInfoRepository.save(fileInfo);
    }
}