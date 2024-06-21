package com.sparta.outsourcing.user.controller;

import com.sparta.outsourcing.user.dto.CommonResponse;
import com.sparta.outsourcing.user.dto.SignupRequestDto;
import com.sparta.outsourcing.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users/signup")
    public ResponseEntity<CommonResponse<Void>> signup(@Valid @RequestBody SignupRequestDto requestDto) {
        CommonResponse<Void> response = new CommonResponse<>("회원가입 성공", 200);
        userService.signup(requestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/board/test")
    public ResponseEntity<String> test() {
        System.out.println("test 입니다.");
        return new ResponseEntity<>("토큰 테스트", HttpStatus.OK);
    }

}