package com.tonyxu.kafka.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.tonyxu.kafka.demo.constant.KafkaDemoConstant.TOPIC_NAME;

/**
 * Created on 2021/1/14.
 *
 * @author <a href="191284969@qq.com">Tony xu</a>
 */
@Service
public class TopicService {

    private final Logger logger = LoggerFactory.getLogger(TopicService.class);

    @Autowired
    private KafkaTemplate<Object, Object> kafkaTemplate;

    public void sendMessage(String message) {
        kafkaTemplate.send(TOPIC_NAME, message);
    }

    @KafkaListener(topics = TOPIC_NAME)
    public void receiveMessage(String message) {
        logger.info("Receive message : {}", message);
    }

}
