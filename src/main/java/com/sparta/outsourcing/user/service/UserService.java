package com.sparta.outsourcing.user.service;

import com.sparta.outsourcing.config.JwtUtil;
import com.sparta.outsourcing.exception.ConflictException;
import com.sparta.outsourcing.security.UserDetailsImpl;
import com.sparta.outsourcing.exception.IncorrectPasswordException;
import com.sparta.outsourcing.exception.NotFoundException;
import com.sparta.outsourcing.user.dto.SignupRequestDto;
import com.sparta.outsourcing.user.dto.WithdrawRequestDto;
import com.sparta.outsourcing.user.dto.UpdatePasswordDto;
import com.sparta.outsourcing.user.dto.UpdateUserRequestDto;
import com.sparta.outsourcing.user.dto.UserResponseDto;
import com.sparta.outsourcing.user.entity.User;
import com.sparta.outsourcing.user.entity.UserStatus;
import com.sparta.outsourcing.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

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

    @Transactional
    public void withdrawal(UserDetailsImpl userDetails, WithdrawRequestDto requestDto) {

        if(!passwordEquals(userDetails, requestDto)) {
            throw new IllegalArgumentException("비밀번호 확인 부탁합니다");
        }

        User user = userRepository.findByUserUidAndPassword(requestDto.getUserUid(), requestDto.getPassword()).orElseThrow(
                () -> new NullPointerException("DB 조회 null인 경우")
        );
        if(!Objects.equals(user, userDetails.getUser())) {
            throw new NullPointerException("같은거 없을 때 뜨는 예외임.");
        }
        user.updateRole(UserStatus.WITHDRAW);
    }

    @Transactional
    public void accessTokenReissue(String refreshToken, HttpServletResponse res) {

        if(!jwtUtil.validateToken(refreshToken)) {
            throw new RuntimeException("재로그인 바람");
        }

        User user = userRepository.findByRefreshToken(refreshToken).orElseThrow(
                () -> new NullPointerException("리프레시 토큰 존재하지 않음. not found exception")
        );

        if(!Objects.equals(user.getRefreshToken(), refreshToken)) {
            throw new RuntimeException("넘어온 토큰하고 DB조회 토큰하고 안맞음");
        }

        String newAccessToken = jwtUtil.createAccessToken(user);
        String newRefreshToken = jwtUtil.createRefreshToken(user);
        res.addHeader(JwtUtil.AUTHORIZATION_HEADER, newAccessToken);
        res.addHeader(JwtUtil.REFRESHTOKEN_HEADER, newRefreshToken);
        String newRefreshTokenOriginal = jwtUtil.refreshTokenSubstring(newRefreshToken);
        user.updateRefreshToken(newRefreshTokenOriginal);
    }

    private boolean passwordEquals(UserDetailsImpl userDetails, WithdrawRequestDto requestDto) {
        return passwordEncoder.matches(userDetails.getUser().getPassword(), requestDto.getPassword());
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