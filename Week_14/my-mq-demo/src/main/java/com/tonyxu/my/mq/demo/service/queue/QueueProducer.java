package com.tonyxu.my.mq.demo.service.queue;

import com.tonyxu.my.mq.demo.domain.Message;
import lombok.AllArgsConstructor;

/**
 * Created on 2021/1/21.
 *
 * @author <a href="191284969@qq.com">Tony xu</a>
 */
@SuppressWarnings("all")
@AllArgsConstructor
public class QueueProducer {

    private final QueueBroker queueBroker;

    /**
     * 发送消息
     *
     * @param topic   消息发送目的地
     * @param message 消息
     * @return 消息是否发送成功
     */
    public boolean sendMessage(String topicName, Message message) {
        QueueMq queueMq = queueBroker.findQueueMq(topicName);
        if (null == queueMq) {
            throw new RuntimeException("Topic[" + topicName + "] doesn't exist.");
        }
        return queueMq.send(message);
    }
}
