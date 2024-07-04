package com.sparta.outsourcing.follow.controller;

import com.sparta.outsourcing.follow.service.FollowService;
import com.sparta.outsourcing.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/users/follows")
public class FollowController {
    private final FollowService followService;
    @GetMapping("/{followUserId}")
    public ResponseEntity<String> toggleUserFollow(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable("followUserId") Long followUserId) {
        return followService.toggleUserFollow(userDetails.getUser(), followUserId);
    }
}
