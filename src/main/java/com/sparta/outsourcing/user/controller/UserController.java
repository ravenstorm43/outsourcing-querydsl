package com.sparta.outsourcing.user.controller;

import com.sparta.outsourcing.security.UserDetailsImpl;
import com.sparta.outsourcing.user.dto.CommonResponse;
import com.sparta.outsourcing.user.dto.RefreshTokenRequest;
import com.sparta.outsourcing.user.dto.SignupRequestDto;
import com.sparta.outsourcing.user.dto.WithdrawRequestDto;
import com.sparta.outsourcing.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<CommonResponse<Void>> signup(@RequestBody @Valid SignupRequestDto requestDto) {
        CommonResponse<Void> response = new CommonResponse<>("회원가입 성공", HttpStatus.OK.value());
        userService.signup(requestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<CommonResponse<Void>> withdrawal(@RequestBody WithdrawRequestDto requestDto) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.withdrawal(userDetails, requestDto);
        CommonResponse<Void> response = new CommonResponse<>("회원탈퇴 성공", HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.logout(userDetails);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/refresh")
    public ResponseEntity<CommonResponse<Void>> refresh(@RequestBody RefreshTokenRequest request, HttpServletResponse res) {
        userService.accessTokenReissue(request.getRefreshToken(), res);
        CommonResponse<Void> response = new CommonResponse<>("Access 토큰 발급 성공", HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}