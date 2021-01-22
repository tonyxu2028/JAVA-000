package com.tonyxu.my.mq.demo;

import com.tonyxu.my.mq.demo.domain.Message;
import com.tonyxu.my.mq.demo.service.array.ArrayBroker;
import com.tonyxu.my.mq.demo.service.array.ArrayConsumer;
import com.tonyxu.my.mq.demo.service.array.ArrayProducer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 自创MQ测试
 *
 * Created on 2021/1/21.
 *
 * @author <a href="191284969@qq.com">Tony xu</a>
 */
@SuppressWarnings("all")
@Slf4j
public class ArrayMqTests {

    private final String TOPIC_NAME = "tonyxu.topic";

    /**
     * 测试生产消息并全部确认
     * expect: 消息可全部被消费
     */
    @Test
    public void testSendAndAck() throws InterruptedException {
        // 创建 Broker
        ArrayBroker broker = new ArrayBroker();
        // 创建 Topic
        broker.createTopic(TOPIC_NAME);
        // 创建消费者
        ArrayConsumer consumer = broker.createConsumer();
        consumer.subscribeMessage(TOPIC_NAME);

        final AtomicBoolean flag = new AtomicBoolean(true);
        new Thread(() -> {
            while (flag.get()) {
                Message message = consumer.receiveMessageAndAck();
                if (message != null) {
                    log.info("Receive message [{}] from [{}].", message.getBody(), TOPIC_NAME);
                }
            }
            log.info("Exit ...");
        }).start();

        // 创建生产者
        ArrayProducer producer = broker.createProducer();
        // 发送消息
        for (int i = 0; i < 10; i++) {
            Message<String> stringMessage = new Message<>();
            stringMessage.setBody("Message<" + i + ">");
            producer.sendMessageAndAck(TOPIC_NAME, stringMessage);
        }
        TimeUnit.MILLISECONDS.sleep(500);
        flag.set(false);
    }

    /**
     * 测试生产消息但全部不确认
     * expect: 消费不到消息
     */
    @Test
    public void testSendAndNoAck() throws InterruptedException {
        // 创建 Broker
        ArrayBroker broker = new ArrayBroker();
        // 创建 Topic
        broker.createTopic(TOPIC_NAME);
        // 创建消费者
        ArrayConsumer consumer = broker.createConsumer();
        consumer.subscribeMessage(TOPIC_NAME);

        final AtomicBoolean flag = new AtomicBoolean(true);
        new Thread(() -> {
            while (flag.get()) {
                Message message = consumer.receiveMessageAndAck();
                if (message != null) {
                    log.info("Receive message [{}] from [{}].", message.getBody(), TOPIC_NAME);
                }
            }
            log.info("Exit ...");
        }).start();

        // 创建生产者
        ArrayProducer producer = broker.createProducer();
        // 发送消息
        for (int i = 0; i < 10; i++) {
            Message<String> stringMessage = new Message<>();
            stringMessage.setBody("Message<" + i + ">");
            producer.sendMessageAndAck(TOPIC_NAME, stringMessage);
        }
        TimeUnit.MILLISECONDS.sleep(500);
        flag.set(false);
    }

    /**
     * 测试生产消息但部分确认
     * expect: 只有被确认的消息才能被消费
     * TODO ... 需保证只有被确认的消息才能被消费
     */
    @Test
    public void testSendAndPartAck() throws InterruptedException {

    }

    /**
     * 测试生产消息并全部确认
     * expect: 消费全部被消息
     */
    @Test
    public void testPollAndAck() throws InterruptedException {
        // 创建 Broker
        ArrayBroker broker = new ArrayBroker();
        // 创建 Topic
        broker.createTopic(TOPIC_NAME);
        // 创建消费者
        ArrayConsumer consumer = broker.createConsumer();
        consumer.subscribeMessage(TOPIC_NAME);

        final AtomicBoolean flag = new AtomicBoolean(true);
        new Thread(() -> {
            while (flag.get()) {
                Message message = consumer.receiveMessageAndAck();
                if (message != null) {
                    log.info("Receive message [{}] from [{}].", message.getBody(), TOPIC_NAME);
                }
            }
            log.info("Exit ...");
        }).start();

        // 创建生产者
        ArrayProducer producer = broker.createProducer();
        // 发送消息
        for (int i = 0; i < 10; i++) {
            Message<String> stringMessage = new Message<>();
            stringMessage.setBody("Message<" + i + ">");
            producer.sendMessageAndAck(TOPIC_NAME, stringMessage);
        }
        TimeUnit.MILLISECONDS.sleep(500);
        flag.set(false);
    }

    /**
     * 测试消费消息但全部不确认
     * expect: 一值消费同一条消息
     */
    @Test
    public void testPollAndNoAck() throws InterruptedException {
        // 创建 Broker
        ArrayBroker broker = new ArrayBroker();
        // 创建 Topic
        broker.createTopic(TOPIC_NAME);
        // 创建消费者
        ArrayConsumer consumer = broker.createConsumer();
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
        ArrayProducer producer = broker.createProducer();
        // 发送消息
        for (int i = 0; i < 10; i++) {
            Message<String> stringMessage = new Message<>();
            stringMessage.setBody("Message<" + i + ">");
            producer.sendMessageAndAck(TOPIC_NAME, stringMessage);
        }
        TimeUnit.MILLISECONDS.sleep(500);
        flag.set(false);
    }

    @Test
    public void testPollByOffset() throws InterruptedException {
        // 创建 Broker
        ArrayBroker broker = new ArrayBroker();
        // 创建 Topic
        broker.createTopic(TOPIC_NAME);
        // 创建消费者
        ArrayConsumer consumer = broker.createConsumer();
        consumer.subscribeMessage(TOPIC_NAME);

        final AtomicBoolean flag = new AtomicBoolean(true);
        new Thread(() -> {
            while (flag.get()) {
                Message message = consumer.receiveMessageFormOffSet();
                if (message != null) {
                    log.info("Receive message [{}] from [{}].", message.getBody(), TOPIC_NAME);
                }
            }
            log.info("Exit ...");
        }).start();
        // 创建生产者
        ArrayProducer producer = broker.createProducer();
        // 发送消息
        for (int i = 0; i < 10; i++) {
            Message<String> stringMessage = new Message<>();
            stringMessage.setBody("Message<" + i + ">");
            producer.sendMessageAndAck(TOPIC_NAME, stringMessage);
        }
        TimeUnit.MILLISECONDS.sleep(500);
        flag.set(false);
    }
}
