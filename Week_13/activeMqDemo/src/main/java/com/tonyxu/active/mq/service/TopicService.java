package com.tonyxu.active.mq.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Topic;

import static com.tonyxu.active.mq.constant.ActiveMqConstant.TOPIC_NAME;

/**
 * Topic Service 服务类
 *
 * Created on 2021/1/14.
 *
 * @author <a href="191284969@qq.com">Tony xu</a>
 */
@SuppressWarnings("ALL")
@Slf4j
@Service
public class TopicService {

    @Autowired
    @SuppressWarnings("unused")
    private JmsTemplate jmsTemplate;

    @Autowired
    private Topic tonyxuTopic;

    public void sendMessage(String message){
        jmsTemplate.convertAndSend(tonyxuTopic,message);
    }

    // @JmsListener 需指定 containerFactory
    @JmsListener(destination = TOPIC_NAME/*, containerFactory = "jmsListenerContainerTopics"*/)
    public void receive1(String message) {
        log.info("Receiver1 receive message : {}", message);
    }

    @JmsListener(destination = TOPIC_NAME, containerFactory = "jmsListenerContainerTopics")
    public void receive2(String message) {
        log.info("Receiver2 receive message : {}", message);
    }

}
