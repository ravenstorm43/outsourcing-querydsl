package com.sparta.outsourcing.user.service;

import com.sparta.outsourcing.user.dto.UserRequestDto;
import com.sparta.outsourcing.user.dto.UserResponseDto;
import com.sparta.outsourcing.user.entity.User;
import com.sparta.outsourcing.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserResponseDto getUser(Long id) {
        User user = findById(id);
        return new UserResponseDto(user);
    }

    @Transactional
    public UserResponseDto updateUser(Long id, UserRequestDto requestDto) {
        User user = findById(id);
        user.updateUser(requestDto);
        return new UserResponseDto(user);
    }
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException()
        );
    }
}
