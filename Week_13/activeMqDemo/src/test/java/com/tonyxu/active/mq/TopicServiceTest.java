package com.tonyxu.active.mq;

import com.tonyxu.active.mq.service.TopicService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TopicServiceTest {

	@Autowired
	private TopicService topicService;

	@Test
	public void testSendMessage() {
		for (int i = 0; i < 10; i++) {
			topicService.sendMessage( i + "message");
		}
	}

}
