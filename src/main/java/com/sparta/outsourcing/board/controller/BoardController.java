package com.sparta.outsourcing.board.controller;

import com.sparta.outsourcing.board.dto.BoardCreateRequest;
import com.sparta.outsourcing.board.dto.BoardDetailResponseDto;
import com.sparta.outsourcing.board.dto.BoardListResponseDto;
import com.sparta.outsourcing.board.dto.BoardUpdateRequest;
import com.sparta.outsourcing.board.service.BoardService;
import com.sparta.outsourcing.security.UserDetailsImpl;
import com.sparta.outsourcing.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService; //캡슐화

    // 게시글 작성
    @PostMapping
    public ResponseEntity<String> create(@Valid  @RequestBody BoardCreateRequest request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        System.out.println(userDetails.getUser().getUserUid());
        System.out.println(userDetails.getUser().getUsername());
        boardService.createBoard(request, user);
        return ResponseEntity.status(201).body("게시글이 작성되었습니다.");
    }

    // 게시글 전체 조회 : 누구든 조회 가능
    @GetMapping
    public ResponseEntity<BoardListResponseDto> getBoardList(
            @RequestParam(defaultValue = "1") int page, //페이지 번호 파라미터
            @RequestParam(defaultValue = "5") int size) { //페이지당 갯수 파라미터
        BoardListResponseDto response = boardService.getAllBoards(page -1, size);
        return ResponseEntity.ok(response);
    }
    // 좋아요를 한 게시글 조회 : 좋아요를 한 본인만 조회가능
    @GetMapping("/users")
    public ResponseEntity<BoardListResponseDto> getBoardListByUser(
            @RequestParam(defaultValue = "1") int page, //페이지 번호 파라미터
            @RequestParam(defaultValue = "5") int size,
            @AuthenticationPrincipal UserDetailsImpl userDetails) { //페이지당 갯수 파라미터
        BoardListResponseDto response = boardService.getAllBoardsByLikedUser(userDetails.getUser(),page -1, size);
        return ResponseEntity.ok(response);
    }
    // 게시글 선택 조회 : 누구든 조회 가능, 하나의 게시글만 조회
    @GetMapping("/{boardId}")
    public ResponseEntity<BoardDetailResponseDto> getBoardDetail(@PathVariable Long boardId) {
        BoardDetailResponseDto response = boardService.getBoardDetail(boardId);
        return ResponseEntity.ok(response);
    }

    // 게시글 수정
    @PutMapping("/{boardId}")
    public ResponseEntity<String> updateBoard(@PathVariable Long boardId, @Valid @RequestBody BoardUpdateRequest request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        boardService.updateBoard(boardId, request, user);
        return ResponseEntity.ok("게시글 수정이 완료되었습니다.");
    }

    // 게시글 삭제
    @DeleteMapping("/{boardId}")
    public ResponseEntity<String> deleteBoard(@PathVariable Long boardId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        boardService.deleteBoard(boardId, user);
        return ResponseEntity.ok("게시글이 삭제되었습니다.");
    }
}
