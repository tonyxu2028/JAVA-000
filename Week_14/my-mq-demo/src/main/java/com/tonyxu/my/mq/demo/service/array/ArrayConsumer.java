package com.tonyxu.my.mq.demo.service.array;

import com.tonyxu.my.mq.demo.domain.Message;
import java.util.concurrent.ConcurrentHashMap;
import static com.tonyxu.my.mq.demo.constant.Constants.DEFAULT_OFFSET_SIGN;

/**
 * 消费者
 *
 * Created on 2021/1/21.
 *
 * @author <a href="191284969@qq.com">Tony xu</a>
 *
 * @param <T> 消息体格式
 *
 */
@SuppressWarnings("all")
public class ArrayConsumer<T> {

    private final ArrayBroker arrayBroker;

    private ArrayMq arrayMq;

    ArrayConsumer(ArrayBroker arrayBroker){
        this.arrayBroker = arrayBroker;
    }

    /**
     * 记录偏移量MAP
     */
    private final ConcurrentHashMap<String,Integer> offsetMap = new ConcurrentHashMap<>();

    /**
     * 消费着订阅消息
     *
     * @param topicName
     */
    public void subscribeMessage(String topicName){
        arrayMq = arrayBroker.findArrayMq(topicName);
        if(arrayMq!=null){
            offsetMap.put(topicName,DEFAULT_OFFSET_SIGN);
        }else {
            throw new RuntimeException(topicName+"doesn't exist.");
        }
    }

    /**
     * 收到消息 Receive Message
     *
     * @return message 接收到的信息
     */
    public Message<T> receiveMessage(){
        return arrayMq.poll();
    }

    /**
     * 收到消息并确认 Receive Message
     *
     * @return message 接收到的信息
     */
    public Message<T> receiveMessageAndAck(){
        Message message = arrayMq.poll();
        if (message != null) {
            arrayMq.consumerAndAck();
            recordOffset();
        }
        return message;
    }

    /**
     * 从offSet处获取收到消息并确认的信息
     *
     * @return message 接收到的信息
     */
    public Message<T> receiveMessageFormOffSet(){
        Message message = arrayMq.poll(offsetMap.get(arrayMq.getTopicName()));
        if (message != null) {
            arrayMq.consumerAndAck();
            recordOffset();
        }
        return message;
    }

    /**
     *
     */
    private synchronized void recordOffset() {
        Integer integer = offsetMap.get(arrayMq.getTopicName());
        offsetMap.put(arrayMq.getTopicName(), ++integer);
    }

}
