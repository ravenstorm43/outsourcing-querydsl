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
    public ResponseEntity<CommonResponse<List<CommentResponseDTO>>> viewAllComment(@PathVariable(value = "boardId") Long boardId,
                                                                                   @RequestParam(defaultValue = "1") int page, //페이지 번호 파라미터
                                                                                   @RequestParam(defaultValue = "5") int size) { //페이지당 갯수 파라미터
        List<CommentResponseDTO> responseDTOList = commentService.viewAllComment(boardId, page - 1, size);
        if(responseDTOList.isEmpty()) {
            CommonResponse<List<CommentResponseDTO>> response = new CommonResponse<>("현재 페이지에 댓글이 없습니다.", HttpStatus.OK.value(),responseDTOList);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        CommonResponse<List<CommentResponseDTO>> response = new CommonResponse<>("댓글조회 성공", HttpStatus.OK.value(),responseDTOList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //좋아요를 한 Comment 조회
    @GetMapping("/boards/comments/users")
    public ResponseEntity<CommonResponse<List<CommentResponseDTO>>> viewAllCommentByLikedUser(@RequestParam(defaultValue = "1") int page, //페이지 번호 파라미터
                                                                                              @RequestParam(defaultValue = "5") int size, //페이지당 갯수 파라미터
                                                                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<CommentResponseDTO> responseDTOList = commentService.viewAllCommentByLikedUser(userDetails.getUser(), page - 1, size);
        if(responseDTOList.isEmpty()) {
            CommonResponse<List<CommentResponseDTO>> response = new CommonResponse<>("현재 페이지에 댓글이 없습니다.", HttpStatus.OK.value(),responseDTOList);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
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
