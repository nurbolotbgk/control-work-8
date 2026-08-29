package com.example.control_work_8.service;

import com.example.control_work_8.dto.RegisterDto;
import com.example.control_work_8.dto.UserDto;

public interface UserService {
    void register(RegisterDto registerDto);

    UserDto getUserByEmail(String email);
}
