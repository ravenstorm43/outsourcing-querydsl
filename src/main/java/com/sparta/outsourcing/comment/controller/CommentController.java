package com.sparta.outsourcing.comment.controller;

import com.sparta.outsourcing.comment.dto.CommentRequestDTO;
import com.sparta.outsourcing.comment.dto.CommentResponseDTO;
import com.sparta.outsourcing.comment.service.CommentService;
import com.sparta.outsourcing.security.UserDetailsImpl;
import com.sparta.outsourcing.user.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<CommonResponse<List<CommentResponseDTO>>> viewAllComment(@PathVariable(value = "boardId") Long boardId) {
        List<CommentResponseDTO> responseDTOList = commentService.viewAllComment(boardId);
        CommonResponse<List<CommentResponseDTO>> response = new CommonResponse<>("댓글조회 성공", HttpStatus.OK.value(),responseDTOList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Comment 작성
    @PostMapping("/boards/{boardId}/comments")
    public CommentResponseDTO createComment(@PathVariable(value = "boardId") Long boardId, @RequestBody CommentRequestDTO commentRequestDTO, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.createComment(boardId, commentRequestDTO, userDetails.getUser());
    }



}
