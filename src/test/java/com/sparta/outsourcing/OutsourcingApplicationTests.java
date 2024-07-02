package com.sparta.outsourcing;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.outsourcing.board.entity.Board;
import com.sparta.outsourcing.common.AnonymousNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.AbstractPageRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.sparta.outsourcing.board.entity.QBoard.board;
import static com.sparta.outsourcing.like.entity.QLike.like;

@SpringBootTest
class OutsourcingApplicationTests {
	@Autowired
	private JPAQueryFactory jpaQueryFactory;

	@Test
	void contextLoads() {
	}

	@Test
	void generateNameTest() {
		System.out.println(AnonymousNameGenerator.nameGenerate());
	}
	@Test
	void boardUserTest() {
		Long userId = 3L;
		Pageable pageable = PageRequest.of(0, 5);
		List<Board> boardList = jpaQueryFactory.selectFrom(board)
				.where(
						board.id.in(jpaQueryFactory
								.select(like.contentId).from(like)
								.where(like.userId.eq(userId))
								.fetch())
				)
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.orderBy(board.createdAt.desc())
				.fetch();
		System.out.println(boardList);
	}
}
