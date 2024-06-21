package com.sparta.outsourcing.comment.service;

import com.sparta.outsourcing.board.entity.Board;
import com.sparta.outsourcing.board.repository.BoardRepository;
import com.sparta.outsourcing.comment.dto.CommentRequestDTO;
import com.sparta.outsourcing.comment.dto.CommentResponseDTO;
import com.sparta.outsourcing.comment.entity.Comment;
import com.sparta.outsourcing.comment.repository.CommentRepository;
import com.sparta.outsourcing.exception.NotFoundException;
import com.sparta.outsourcing.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;


    //댓글 전체 조회
    public List<CommentResponseDTO> viewAllComment(Long boardId) {
        boardRepository.findById(boardId).orElseThrow(
                () -> new NotFoundException("선택한 게시물이 없습니다.")
        );

        List<Comment> comments = commentRepository.findAllByBoardId(boardId);
        return comments.stream().map(CommentResponseDTO::new).toList();
    }

    // 댓글 작성
    public CommentResponseDTO createComment(Long boardId, CommentRequestDTO commentRequestDTO, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new NotFoundException("선택한 게시물이 없습니다.")
        );
        Comment comment = new Comment(board, commentRequestDTO, user);
        Comment saveComment = commentRepository.save(comment);
        return new CommentResponseDTO(saveComment);
    }

    // 댓글 수정
    @Transactional
    public CommentResponseDTO updateComment(Long boardId,Long commentId, CommentRequestDTO commentRequestDTO, User user) {
        boardRepository.findById(boardId).orElseThrow(
                () -> new NotFoundException("선택한 게시물이 없습니다.")
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NotFoundException("조회한 댓글이 없습니다.")
        );

        if (Objects.equals(comment.getUser().getUserUid(), user.getUserUid())) {
            comment.updateComment(commentRequestDTO);
        } else {
            throw new NotFoundException("사용자 ID가 일치하지 않습니다.");
        }
        return new CommentResponseDTO(comment);
    }
}
