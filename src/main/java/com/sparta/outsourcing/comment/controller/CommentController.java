package com.sparta.outsourcing.comment.controller;

import com.sparta.outsourcing.comment.dto.CommentResponseDTO;
import com.sparta.outsourcing.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
