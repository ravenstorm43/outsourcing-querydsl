package com.sparta.outsourcing.user.controller;

import com.sparta.outsourcing.security.UserDetailsImpl;
import com.sparta.outsourcing.user.dto.CommonResponse;
import com.sparta.outsourcing.user.dto.UpdatePasswordDto;
import com.sparta.outsourcing.user.dto.UpdateUserRequestDto;
import com.sparta.outsourcing.user.dto.UserResponseDto;
import com.sparta.outsourcing.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProfileController {
    private final UserService userService;
    @GetMapping("/profile")
    public ResponseEntity<CommonResponse<UserResponseDto>> getUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommonResponse<UserResponseDto> response = new CommonResponse<>("성공", 200, userService.getUser(userDetails.getUser()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/profile")
    public ResponseEntity<CommonResponse<UserResponseDto>> updateUser(@AuthenticationPrincipal UserDetailsImpl userDetails, @Valid @RequestBody UpdateUserRequestDto requestDto) {
        CommonResponse<UserResponseDto> response = new CommonResponse<>("프로필 수정 완료", 200, userService.updateUser(userDetails.getUser().getId(), requestDto));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/profile/password")
    public ResponseEntity<CommonResponse<Void>> resetPassword(@AuthenticationPrincipal UserDetailsImpl userDetails, @Valid @RequestBody UpdatePasswordDto requestDto) {
        userService.updatePassword(userDetails.getUser().getId(), requestDto);
        CommonResponse<Void> response = new CommonResponse<>("비밀번호 변경 완료", 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
