package com.tonyxu.my.mq.demo.service.queue;

import com.tonyxu.my.mq.demo.domain.Message;
import java.util.Objects;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created on 2021/1/21.
 *
 * @author <a href="191284969@qq.com">Tony xu</a>
 */
@SuppressWarnings("all")
public class QueueMq {

    private BlockingDeque<Message> queue;

    private String topic;

    private int capacity;

    QueueMq(String topic, int capacity) {
        this.topic = Objects.requireNonNull(topic, "Topic's name can not be null!");
        this.capacity = capacity;
        queue = new LinkedBlockingDeque<>(capacity);
    }

    public boolean send(Message message) {
        return queue.offer(message);
    }

    public Message poll() {
        queue.peek();
        return queue.poll();
    }

}
