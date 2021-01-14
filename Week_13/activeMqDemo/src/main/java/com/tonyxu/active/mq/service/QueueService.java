package com.tonyxu.active.mq.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import javax.jms.TextMessage;
import static com.tonyxu.active.mq.constant.ActiveMqConstant.QUEUE_NAME;

/**
 * Queue Service 服务类
 *
 * Created on 2021/1/14.
 *
 * @author <a href="191284969@qq.com">Tony xu</a>
 */
@SuppressWarnings("ALL")
@Slf4j
@Service
public class QueueService {

    @Autowired
    @SuppressWarnings("unused")
    private JmsTemplate jmsTemplate;

    /**
     * ActiveMq 发送消息
     * @param message
     * @return
     */
    public String sendMessage(String message){
        jmsTemplate.send(QUEUE_NAME,session -> {
            TextMessage textMessage = session.createTextMessage();
            textMessage.setText(message);
            return textMessage;
        });
        return message;
    }

}
