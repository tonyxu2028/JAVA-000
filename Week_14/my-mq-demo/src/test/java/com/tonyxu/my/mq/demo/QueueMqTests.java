package com.tonyxu.my.mq.demo;

import com.tonyxu.my.mq.demo.domain.Message;
import com.tonyxu.my.mq.demo.service.queue.QueueBroker;
import com.tonyxu.my.mq.demo.service.queue.QueueConsumer;
import com.tonyxu.my.mq.demo.service.queue.QueueProducer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created on 2021/1/21.
 *
 * @author <a href="191284969@qq.com">Tony xu</a>
 */
@SuppressWarnings("all")
@Slf4j
public class QueueMqTests {
    private final String TOPIC_NAME = "tonyxu.topic";

    @Test
    public void testLocalMq() throws InterruptedException {
        // 创建 Broker
        QueueBroker broker = new QueueBroker();
        // 创建 Topic
        broker.createTopic(TOPIC_NAME);
        // 创建消费者
        QueueConsumer consumer = broker.createConsumer();
        consumer.subscribeMessage(TOPIC_NAME);

        final AtomicBoolean flag = new AtomicBoolean(true);
        new Thread(() -> {
            while (flag.get()) {
                Message message = consumer.receiveMessage();
                if (message != null) {
                    log.info("Receive message [{}] from [{}].", message.getBody(), TOPIC_NAME);
                }
            }
            log.info("Exit ...");
        }).start();

        // 创建生产者
        QueueProducer producer = broker.createProducer();
        // 发送消息
        for (int i = 0; i < 10; i++) {
            Message<String> stringMessage = new Message<>();
            stringMessage.setBody("Message<" + i + ">");
            producer.sendMessage(TOPIC_NAME, stringMessage);
        }
        TimeUnit.MILLISECONDS.sleep(500);
        flag.set(false);
    }
}
