package com.example.control_work_8.controller;

import com.example.control_work_8.dto.FileInfoDto;
import com.example.control_work_8.dto.UserDto;
import com.example.control_work_8.service.FileInfoService;
import com.example.control_work_8.service.UserService;
import lombok.RequiredArgsConstructor;
import org.h2.engine.Mode;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final UserService userService;
    private final FileInfoService fileInfoService;

    @GetMapping
    public String profile(Authentication authentication, @RequestParam(defaultValue = "0") int page,
                          Model model) {
        String email = authentication.getName();
        UserDto user = userService.getUserByEmail(email);
        Page<FileInfoDto> files = fileInfoService.getFilesByUser(email,page, 5);
        model.addAttribute("user", user);
        model.addAttribute("files", files.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", files.getTotalPages());

        return "profile/profile";


    }

}
