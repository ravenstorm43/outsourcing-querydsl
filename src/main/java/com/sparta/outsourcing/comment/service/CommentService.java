package com.sparta.outsourcing.comment.service;

import com.sparta.outsourcing.comment.dto.CommentRequestDTO;
import com.sparta.outsourcing.comment.dto.CommentResponseDTO;
import com.sparta.outsourcing.comment.entity.Comment;
import com.sparta.outsourcing.comment.repository.CommentRepository;
import com.sparta.outsourcing.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;


    //댓글 전체 조회
    public List<CommentResponseDTO> viewAllComment(Long boardId) {
        boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("선택한 게시물이 없습니다.")
        );

        List<Comment> comments = commentRepository.findAllByBoardId(boardId);
        return comments.stream().map(CommentResponseDTO::new).toList();
    }

    // 댓글 작성
    public CommentResponseDTO createComment(Long boardId, CommentRequestDTO commentRequestDTO, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("선택한 게시물이 없습니다.")
        );
        Comment comment = new Comment(board, commentRequestDTO, user);
        Comment saveComment = commentRepository.save(comment);
        return new CommentResponseDTO(saveComment);
    }

}
