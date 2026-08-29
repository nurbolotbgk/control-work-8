package com.example.control_work_8.controller;

import com.example.control_work_8.dto.FileInfoDto;
import com.example.control_work_8.service.FileInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
}