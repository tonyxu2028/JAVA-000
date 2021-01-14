package com.tonyxu.active.mq;

import com.tonyxu.active.mq.service.QueueService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class QueueServiceTest {

	@Autowired
	private QueueService queueService;

	@Test
	public void testSendMessage() {
		for (int i = 0; i < 10; i++) {
			queueService.sendMessage( i + "message");
		}
	}

}
