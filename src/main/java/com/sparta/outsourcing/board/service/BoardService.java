package com.sparta.outsourcing.board.service;

import com.sparta.outsourcing.board.dto.BoardCreateRequest;
import com.sparta.outsourcing.board.dto.BoardDetailResponseDto;
import com.sparta.outsourcing.board.dto.BoardListResponseDto;
import com.sparta.outsourcing.board.dto.BoardUpdateRequest;
import com.sparta.outsourcing.board.entity.Board;
import com.sparta.outsourcing.board.repository.BoardRepository;
import com.sparta.outsourcing.common.AnonymousNameGenerator;
import com.sparta.outsourcing.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public Long createBoard(BoardCreateRequest request, User user) {
        Board board = new Board();
        board.setTitle(request.getTitle());
        board.setContent(request.getContent());
        board.setUser(user);
        board.setGeneratedname(AnonymousNameGenerator.nameGenerate());
        Board savedBoard = boardRepository.save(board);
        return savedBoard.getId();
    }

    public BoardListResponseDto getAllBoards(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Board> boardPage = boardRepository.findAll(pageable);

        if (page > 0 && page >= boardPage.getTotalPages()) { //잘못된 페이지 요청 시 예외 처리
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 페이지를 찾을 수 없습니다.");
        }

        List<BoardListResponseDto.BoardData> boardDataList = boardPage.getContent().stream()
                .map(board -> new BoardListResponseDto.BoardData(
                        board.getId(),
                        board.getTitle(),
                        board.getGeneratedname(),
                        board.getUpdatedAt()
                ))
                .collect(Collectors.toList());

        if (boardDataList.isEmpty()) { //빈 페이지 메시지
            return new BoardListResponseDto(200, "현재 페이지에 게시글이 없습니다.", boardDataList);
        }

        return new BoardListResponseDto(200, "전체 게시글 조회 성공", boardDataList);
    }
    public BoardDetailResponseDto getBoardDetail(Long boardId) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        if (optionalBoard.isPresent()) {
            Board board = optionalBoard.get();
            BoardDetailResponseDto.BoardData boardData = new BoardDetailResponseDto.BoardData(
                    board.getTitle(),
                    board.getContent(),
                    board.getGeneratedname(),
                    board.getUpdatedAt()
            );
            return new BoardDetailResponseDto(200, "게시글 조회 성공", boardData);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, boardId + "번 게시글을 찾을 수 없습니다.");
        }
    }

    public void updateBoard(Long boardId, BoardUpdateRequest request, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."));
        if (!board.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "작성자만 수정 가능합니다.");
        }
        board.setTitle(request.getTitle());
        board.setContent(request.getContent());
        boardRepository.save(board);
    }

    public void deleteBoard(Long boardId, User user) { // 게시글 삭제 기능 추가
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."));
        if (!board.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "작성자만 삭제 가능합니다.");
        }
        boardRepository.delete(board);
    }
}
