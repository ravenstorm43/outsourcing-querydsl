package com.sparta.outsourcing.comment.controller;

import com.sparta.outsourcing.comment.dto.CommentRequestDTO;
import com.sparta.outsourcing.comment.dto.CommentResponseDTO;
import com.sparta.outsourcing.comment.service.CommentService;
import com.sparta.outsourcing.security.UserDetailsImpl;
import com.sparta.outsourcing.user.dto.CommonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
    public ResponseEntity<CommonResponse<CommentResponseDTO>> createComment(@PathVariable(value = "boardId") Long boardId,
                                            @RequestBody @Valid CommentRequestDTO commentRequestDTO,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommentResponseDTO comment = commentService.createComment(boardId, commentRequestDTO, userDetails.getUser());
        CommonResponse<CommentResponseDTO> response = new CommonResponse<>("댓글이 작성되었습니다.", HttpStatus.CREATED.value(),comment);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //Comment 수정
    @PutMapping("/boards/{boardId}/comments/{commentId}")
    public ResponseEntity<CommonResponse<CommentResponseDTO>> updateComment(@PathVariable(value = "boardId") Long boardId,
                                                                           @PathVariable(value = "commentId") Long commentId,
                                                                           @RequestBody @Valid CommentRequestDTO commentRequestDTO,
                                                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommentResponseDTO comment = commentService.updateComment(boardId, commentId, commentRequestDTO, userDetails.getUser());
        CommonResponse<CommentResponseDTO> response = new CommonResponse<>("댓글 수정이 완료되었습니다.", HttpStatus.OK.value(), comment);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Comment 삭제
    @DeleteMapping("/boards/{boardId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable(value = "boardId") Long boardId,
                                                @PathVariable(value = "commentId") Long commentId,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.deleteComment(boardId, commentId, userDetails.getUser());
        return ResponseEntity.ok("댓글이 삭제 되었습니다.");
    }




}
