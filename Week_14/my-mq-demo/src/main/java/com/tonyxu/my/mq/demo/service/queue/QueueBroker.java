package com.tonyxu.my.mq.demo.service.queue;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import static com.tonyxu.my.mq.demo.constant.Constants.QUEUE_CAPACITY;

/**
 * Created on 2021/1/21.
 *
 * @author <a href="191284969@qq.com">Tony xu</a>
 */
@SuppressWarnings("all")
public class QueueBroker {

    private final Map<String, QueueMq> mqMap = new ConcurrentHashMap<>(64);

    public void createTopic(String name) {
        mqMap.putIfAbsent(name, new QueueMq(name, QUEUE_CAPACITY));
    }

    public QueueProducer createProducer() {
        return new QueueProducer(this);
    }

    public QueueConsumer createConsumer() {
        return new QueueConsumer(this);
    }

    QueueMq findQueueMq(String topic) {
        return this.mqMap.get(topic);
    }
}
