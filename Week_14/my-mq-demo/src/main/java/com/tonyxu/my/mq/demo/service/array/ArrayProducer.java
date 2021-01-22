package com.tonyxu.my.mq.demo.service.array;

import com.tonyxu.my.mq.demo.domain.Message;
import lombok.AllArgsConstructor;

/**
 * 消费生产者
 *
 * Created on 2021/1/21.
 *
 * @author <a href="191284969@qq.com">Tony xu</a>
 */
@SuppressWarnings("all")
@AllArgsConstructor
public class ArrayProducer {

    private final ArrayBroker arrayBroker;

    /**
     * Send Message 生产消息
     * @param topic
     * @param message
     * @return
     */
    public boolean sendMessage(String topicName, Message message){
        ArrayMq arrayMq = arrayBroker.findArrayMq(topicName);
        if(arrayMq == null){
            throw new RuntimeException("Topic[" + topicName + "] doesn't exist.");
        }
        return arrayMq.send(message);
    }

    /**
     * Send Message And Ack 生产消息并且确认收到结果
     * @param topic
     * @param message
     * @return
     */
    public boolean sendMessageAndAck(String topicName,Message message){
        ArrayMq arrayMq = arrayBroker.findArrayMq(topicName);
        if(arrayMq == null){
            throw  new RuntimeException("Topic[" + topicName + "] doesn't exist.");
        }
        boolean send = arrayMq.send(message);
        if (send) {
            arrayMq.producerAndAck();
        }
        return send;
    }

}
