package com.sparta.outsourcing.user.service;

import com.sparta.outsourcing.exception.ConflictException;
import com.sparta.outsourcing.exception.IncorrectPasswordException;
import com.sparta.outsourcing.exception.NotFoundException;
import com.sparta.outsourcing.user.dto.SignupRequestDto;
import com.sparta.outsourcing.user.dto.UpdatePasswordDto;
import com.sparta.outsourcing.user.dto.UpdateUserRequestDto;
import com.sparta.outsourcing.user.dto.UserResponseDto;
import com.sparta.outsourcing.user.entity.User;
import com.sparta.outsourcing.user.entity.UserStatus;
import com.sparta.outsourcing.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(SignupRequestDto requestDto) {
        String password = passwordEncoder.encode(requestDto.getPassword());

        Optional<User> checkUser = userRepository.findByUserUid(requestDto.getUserUid());

        checkUser.ifPresent(user -> {
            if(user.getRole() == UserStatus.WITHDRAW) {
                throw new ConflictException("중복된 사용자가 존재합니다.");
            }
            throw new ConflictException("중복된 사용자가 존재합니다.");
        });

        userRepository.save(new User(requestDto, password));
    }
    public UserResponseDto getUser(User user) {
        return new UserResponseDto(user);
    }

    @Transactional
    public UserResponseDto updateUser(Long id, UpdateUserRequestDto requestDto) {
        User user = findById(id);
        user.updateUser(requestDto);
        return new UserResponseDto(user);
    }
    @Transactional
    public void updatePassword(Long id, UpdatePasswordDto requestDto) {
        User user = findById(id);

        if (!passwordEncoder.matches(requestDto.getOldpassword(), user.getPassword())) {
            throw new IncorrectPasswordException("비밀번호가 일치하지 않습니다.");
        }

        if (passwordEncoder.matches(requestDto.getNewpassword(), user.getPassword())) {
            throw new ConflictException("중복된 비밀번호 입니다.");
        }

        user.updatePassword(passwordEncoder.encode(requestDto.getNewpassword()));
    }
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("사용자를 찾을 수 없습니다")
        );
    }
}