package com.sparta.outsourcing.oauth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.outsourcing.config.JwtUtil;
import com.sparta.outsourcing.oauth.dto.KakaoUserInfoDto;
import com.sparta.outsourcing.oauth.dto.TokenResponseDto;
import com.sparta.outsourcing.oauth.service.KakaoService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class KakaoController {
    private final KakaoService kakaoService;
    @GetMapping("/oauth")
    public ResponseEntity<String> kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        // code: 카카오 서버로부터 받은 인가 코드 Service 전달 후 인증 처리 및 JWT 반환
        TokenResponseDto tokenDto = kakaoService.kakaoLogin(code);
        response.addHeader(JwtUtil.REFRESHTOKEN_HEADER, tokenDto.getRefreshToken());
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, tokenDto.getAccessToken());
        response.setStatus(HttpStatus.OK.value());

        return new ResponseEntity<>("로그인 성공" + HttpStatus.OK.value(), HttpStatus.OK);
    }
}
