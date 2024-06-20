package com.sparta.outsourcing.comment.controller;

import com.sparta.outsourcing.comment.dto.CommentRequestDTO;
import com.sparta.outsourcing.comment.dto.CommentResponseDTO;
import com.sparta.outsourcing.comment.service.CommentService;
import com.sparta.outsourcing.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    //Comment 전체 조회
    @GetMapping("/boards/{boardId}/comments")
    public List<CommentResponseDTO> viewAllComment(@PathVariable(value = "boardId") Long boardId) {
        return commentService.viewAllComment(boardId);
    }

    //Comment 작성
    @PostMapping("/boards/{boardId}/comments")
    public CommentResponseDTO createComment(@PathVariable(value = "boardId") Long boardId, @RequestBody CommentRequestDTO commentRequestDTO, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.createComment(boardId, commentRequestDTO, userDetails.getUser());
    }



}
