package com.tonyxu.my.mq.demo.service.array;

import com.tonyxu.my.mq.demo.domain.Message;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 内存 MQ，基于数组实现
 *
 * Created on 2021/1/21.
 *
 * @author <a href="191284969@qq.com">Tony xu</a>
 */
@SuppressWarnings("all")
@Getter
@Setter
@ToString
@Slf4j
public class ArrayMq {

    private String topicName;

    private int capacity;

    /**
     * 队列消息容量
     */
    int size;

    /**
     * 读取元素的位置
     */
    int nextReadIndex;

    /**
     * 写入元素的位置
     */
    int nextWriteIndex;

    /**
     * 记录可读边界
     */
    int readBound;

    final Lock lock = new ReentrantLock();

    private Message[] queue;

    /**
     * ArrayMq 构造子
     * @param topicName topic名称
     * @param capacity  容量
     */
    ArrayMq(String topicName,int capacity){
        this.topicName = Objects.requireNonNull(topicName, "Topic's name must not be null!");
        if (capacity <= 0) {
            throw new RuntimeException("Capacity must greater than zero!");
        }
        this.capacity = capacity;
        queue = new Message[capacity];
    }

    /**
     * ArrayMq 发送消息
     * @param message 消息
     * @return 是否生产消息到队列
     */
    public boolean send(Message message){
        lock.lock();
        try{
            // 当size到达Queue容量,抛出Queue is Full异常
            if(size == capacity){
                throw new RuntimeException("Queue is Full");
            }
            // 将message放入Queue队列
            queue[nextReadIndex++] = message;
            // 当nextReadIndex到达capacity的容量,重置nextReadIndex
            resetNextReadIndex(nextReadIndex);
            size++;
            log.debug("After write queue: {}", Arrays.toString(queue));
            return true;
        }catch (Exception e){
            log.info(e.getMessage());
            return false;
        }finally {
            lock.unlock();
        }
    }

    /**
     * 重置nextReadIndex
     * @param nextReadIndex
     */
    private void resetNextReadIndex(int nextReadIndex){
        if(nextReadIndex == capacity){
            nextReadIndex = 0;
        }
    }

    /**
     * 生产者确认消息
     * @return 生产者消息结果确认
     */
    public boolean producerAndAck(){
        lock.lock();
        try {
            // 生产者确认后，可读边界后移
            readBound++;
        } catch (Exception e){
            log.info(e.getMessage());
            return false;
        }finally {
            lock.unlock();
        }
        return true;
    }

    /**
     * poll
     * @return
     */
    public Message poll(){
        lock.lock();
        try{
            // 消息空队列
            if (size == 0) {
                throw new RuntimeException("Queue is empty!");
            }
            // 消息没有确认
            if (nextReadIndex == readBound) {
                throw new RuntimeException("Message is not ack");
            }
            Message message = queue[nextReadIndex];
            log.debug("After read queue: {}", Arrays.toString(queue));
            return message;
        }catch (Exception e){
            log.info(e.getMessage());
            return null;
        }finally {
            lock.unlock();
        }
    }

    /**
     * poll
     * @param offset
     * @return
     */
    public Message poll(int offset){
        lock.lock();
        try{
            // 消息空队列
            if (size == 0) {
                throw new RuntimeException("Queue is empty!");
            }
            // 消息没有确认
            if (offset == readBound) {
                throw new RuntimeException("Message is not ack");
            }
            Message message = queue[offset];
            log.debug("After read queue: {}", Arrays.toString(queue));
            return message;
        }catch (Exception e){
            log.info(e.getMessage());
            return null;
        }finally {
            lock.unlock();
        }
    }

    /**
     * Consumer And Ack
     * @return
     */
    public boolean consumerAndAck(){
        lock.lock();
        try {
            // 消费者确认后，删除确认消息，下次读取位置后移
            queue[nextReadIndex] = null;
            if (++nextReadIndex == capacity) {
                nextReadIndex = 0;
            }
            size--;
        } finally {
            lock.unlock();
        }
        return true;
    }

}
