package com.sparta.outsourcing.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.outsourcing.config.JwtUtil;
import com.sparta.outsourcing.user.dto.CommonResponse;
import com.sparta.outsourcing.user.dto.LoginRequestDto;
import com.sparta.outsourcing.user.dto.LoginResponseDto;
import com.sparta.outsourcing.user.entity.User;
import com.sparta.outsourcing.user.entity.UserStatus;
import com.sparta.outsourcing.user.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Objects;

@Slf4j(topic = "JwtAuthenticationFilter")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        setFilterProcessesUrl("/api/users/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            if(!(Objects.equals(request.getMethod(), "POST"))) {
                throw new AuthenticationServiceException("잘못된 HTTP 요청");
            }

            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getUserUid(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) throws IOException
    {
        User user = ((UserDetailsImpl) authResult.getPrincipal()).getUser();

        if (isInvalidUser(user, response)) {
            return;
        }
        String accessToken = jwtUtil.createAccessToken(user);
        String refreshToken = jwtUtil.createRefreshToken(user);

        updateRefreshToken(user, refreshToken);
        setSuccessfulResponse(user, accessToken, refreshToken, response);
    }

    private void updateRefreshToken(User user, String refreshToken) {
        String originalRefreshToken = jwtUtil.refreshTokenSubstring(refreshToken);
        user.updateRefreshToken(originalRefreshToken);
        userRepository.save(user);
        log.info("{}", "refreshToken 업데이트 성공");
    }

    private void setSuccessfulResponse(
            User user,
            String accessToken,
            String refreshToken,
            HttpServletResponse response) throws IOException
    {
        CommonResponse<LoginResponseDto> commonResponse = new CommonResponse<>(
                "로그인 성공", HttpStatus.OK.value(),
                new LoginResponseDto(user.getUserUid(), user.getUsername(), user.getRole())
        );
        response.addHeader(JwtUtil.REFRESHTOKEN_HEADER, refreshToken);
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, accessToken);
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("text/plain;charset=UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(commonResponse));
    }

    private boolean isInvalidUser(User user, HttpServletResponse response) throws IOException {
        if(Objects.equals(user.getRole(), UserStatus.WITHDRAW)) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().write("삭제된 계정입니다.");
            return true;
        }
        return false;
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(401);
    }

}
