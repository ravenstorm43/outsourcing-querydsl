package com.sparta.outsourcing.board.repository;

import com.sparta.outsourcing.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
