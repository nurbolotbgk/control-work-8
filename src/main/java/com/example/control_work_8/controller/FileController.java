package com.example.control_work_8.controller;

import com.example.control_work_8.dto.FileInfoDto;
import com.example.control_work_8.service.FileInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequiredArgsConstructor
public class FileController {

    private final FileInfoService fileInfoService;

    @PostMapping("/files/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, @RequestParam(defaultValue = "false") Boolean isPublic,
            Authentication authentication
    ) throws IOException {

        String email = authentication.getName();

        fileInfoService.uploadFile(file, isPublic, email);

        return "redirect:/profile";
    }

    @GetMapping("/files/public")
    public String getPublicFiles(@RequestParam(defaultValue = "0") int page, Model model) {

        Page<FileInfoDto> files = fileInfoService.getPublicFiles(page, 5);

        model.addAttribute("files", files.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", files.getTotalPages());

        return "files/public";
    }

    @GetMapping("/files/public/{id}")
    public ResponseEntity<Resource> downloadPublicFile(@PathVariable Long id) throws IOException {

        FileInfoDto fileInfo = fileInfoService.getPublicFileById(id);

        Path path = Paths.get("uploads", fileInfo.getStoredName());

        Resource resource = new UrlResource(path.toUri());

        if (!resource.exists()) {
            throw new IllegalArgumentException("Файл не найден");
        }

        fileInfoService.addDownloadCount(id);

        MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;

        if (fileInfo.getContentType() != null) {
            mediaType = MediaType.parseMediaType(fileInfo.getContentType());
        }

        return ResponseEntity.ok()
                .contentType(mediaType)
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" +
                                fileInfo.getOriginalName() +
                                "\""
                )
                .body(resource);
    }

    @GetMapping("/files/private/{shareKey}")
    public ResponseEntity<Resource> downloadPrivateFile(@PathVariable String shareKey) throws IOException {

        FileInfoDto fileInfo = fileInfoService.getPrivateFileByKey(shareKey);

        Path path = Paths.get("uploads", fileInfo.getStoredName());

        Resource resource = new UrlResource(path.toUri());

        if (!resource.exists()) {
            throw new IllegalArgumentException("Файл не найден");
        }

        fileInfoService.addDownloadCount(fileInfo.getId());
        fileInfoService.deleteShareKey(fileInfo.getId());

        MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;

        if (fileInfo.getContentType() != null) {
            mediaType = MediaType.parseMediaType(fileInfo.getContentType());
        }

        return ResponseEntity.ok()
                .contentType(mediaType)
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" +
                                fileInfo.getOriginalName() +
                                "\""
                )
                .body(resource);
    }
}