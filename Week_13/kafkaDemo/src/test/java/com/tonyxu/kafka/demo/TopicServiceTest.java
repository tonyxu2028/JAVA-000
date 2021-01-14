package com.tonyxu.kafka.demo;

import com.tonyxu.kafka.demo.service.TopicService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Created on 2021/1/14.
 *
 * @author <a href="191284969@qq.com">Tony xu</a>
 */
@SpringBootTest
public class TopicServiceTest {

    @Autowired
    private TopicService topicService;

    @Test
    public void testSendMessage() {

        for (int i = 0; i < 100; i++) {
            topicService.sendMessage("message" + i);
        }
    }

}
