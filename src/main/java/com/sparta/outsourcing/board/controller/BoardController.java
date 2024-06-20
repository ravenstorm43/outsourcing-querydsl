package com.sparta.outsourcing.board.controller;

import com.sparta.outsourcing.board.dto.BoardCreateRequest;
import com.sparta.outsourcing.board.service.BoardService;
import com.sparta.outsourcing.security.UserDetailsImpl;
import com.sparta.outsourcing.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {
    public final BoardService boardService;

    // 게시글 작성
    @PostMapping
    public ResponseEntity<String> create(@RequestBody BoardCreateRequest request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        System.out.println(userDetails.getUser().getUserUid());
        System.out.println(userDetails.getUser().getUsername());
        boardService.createBoard(request, user);
        return ResponseEntity.status(201).body("게시글이 작성되었습니다.");
    }
}
