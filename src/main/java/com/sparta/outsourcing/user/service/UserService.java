package com.sparta.outsourcing.user.service;

import com.sparta.outsourcing.config.JwtUtil;
import com.sparta.outsourcing.exception.*;
import com.sparta.outsourcing.security.UserDetailsImpl;
import com.sparta.outsourcing.user.dto.SignupRequestDto;
import com.sparta.outsourcing.user.dto.WithdrawRequestDto;
import com.sparta.outsourcing.user.dto.UpdatePasswordDto;
import com.sparta.outsourcing.user.dto.UpdateUserRequestDto;
import com.sparta.outsourcing.user.dto.UserResponseDto;
import com.sparta.outsourcing.user.entity.User;
import com.sparta.outsourcing.user.entity.UserStatus;
import com.sparta.outsourcing.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

        if(!passwordEquals(requestDto, userDetails)) {
            throw new DataDifferentException("비밀번호가 다릅니다.");
        }

        User user = userRepository.findByUserUidAndPassword(requestDto.getUserUid(), userDetails.getPassword()).orElseThrow(
                () -> new NotFoundException("조회된 회원의 정보가 없습니다.")
        );

        user.updateRole(UserStatus.WITHDRAW);
    }

    @Transactional
    public void accessTokenReissue(String refreshToken, HttpServletResponse res) {
        String originalRefreshToken = jwtUtil.refreshTokenSubstring(refreshToken);

        if(!jwtUtil.validateToken(originalRefreshToken)) {
            throw new InvalidTokenException("로그인이 필요합니다.");
        }

//        String originalRefreshToken = jwtUtil.refreshTokenSubstring(refreshToken);
//        Claims userInfo = jwtUtil.getUserInfoFromToken(originalRefreshToken);
//        String userUid = userInfo.getSubject();

        User user = userRepository.findByRefreshToken(originalRefreshToken).orElseThrow(
                () -> new NotFoundException("해당 유저의 리프레시 토큰 정보가 없습니다.")
        );

        String newAccessToken = jwtUtil.createAccessToken(user);
        String newRefreshToken = jwtUtil.createRefreshToken(user);
        res.addHeader(JwtUtil.AUTHORIZATION_HEADER, newAccessToken);
        res.addHeader(JwtUtil.REFRESHTOKEN_HEADER, newRefreshToken);
        String newRefreshTokenOriginal = jwtUtil.refreshTokenSubstring(newRefreshToken);
        user.updateRefreshToken(newRefreshTokenOriginal);
    }

    private boolean passwordEquals(WithdrawRequestDto requestDto, UserDetailsImpl userDetails) {
        return passwordEncoder.matches(requestDto.getPassword(), userDetails.getUser().getPassword());
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

    @Transactional
    public void logout(UserDetailsImpl userDetails) {
        User user = userRepository.findById(userDetails.getUser().getId()).orElseThrow(
                () -> new NotFoundException("사용자를 찾을 수 없습니다.")
        );
        user.updateRefreshToken(null);
    }
}