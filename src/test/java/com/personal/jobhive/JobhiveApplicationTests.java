package com.personal.jobhive;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

import com.personal.jobhive.services.EmailService;

@SpringBootTest
class JobhiveApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private EmailService service;

	@Test
	void sendEmailTest() {
		service.sendEmail(
				"pranshu.itspp@gmail.com",
				"Just managing the emails",
				"this is test project working on email service");
	}
}
