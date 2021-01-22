package com.tonyxu.my.mq.demo.service.queue;

import com.tonyxu.my.mq.demo.domain.Message;

/**
 * Created on 2021/1/21.
 *
 * @author <a href="191284969@qq.com">Tony xu</a>
 */
@SuppressWarnings("all")
public class QueueConsumer<T> {

    private final QueueBroker queueBroker;

    private QueueMq queueMq;

    QueueConsumer(QueueBroker queueBroker) {
        this.queueBroker = queueBroker;
    }

    public void subscribeMessage(String topicName){
        QueueMq queueMq = queueBroker.findQueueMq(topicName);
        if (queueMq == null) {
            throw new RuntimeException("Topic[" + topicName + "] doesn't exist.");
        }
    }

    public Message<T> receiveMessage() {
        return queueMq.poll();
    }
}
