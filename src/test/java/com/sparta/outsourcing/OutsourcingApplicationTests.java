package com.sparta.outsourcing;

import com.sparta.outsourcing.common.AnonymousNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OutsourcingApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void generateNameTest() {
		System.out.println(AnonymousNameGenerator.nameGenerate());
	}
	@Test
	void boardUserTest() {

	}
}
