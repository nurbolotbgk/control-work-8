package com.example.control_work_8.service.impl;


import com.example.control_work_8.dto.RegisterDto;
import com.example.control_work_8.dto.UserDto;
import com.example.control_work_8.model.User;
import com.example.control_work_8.repository.UserRepository;
import com.example.control_work_8.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(RegisterDto registerDto) {
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new IllegalArgumentException("Пользователь с таким email уже существует");
        }

        User user = new User();
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setEnabled(true);

        userRepository.save(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();

        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .enabled(user.getEnabled())
                .build();
    }
}
