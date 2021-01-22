package com.tonyxu.my.mq.demo.service.array;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.tonyxu.my.mq.demo.constant.Constants.ARRAY_CAPACITY;

/**
 * 消息服务:模拟队列的数组
 *
 * Created on 2021/1/21.
 *
 * @author <a href="191284969@qq.com">Tony xu</a>
 */
@SuppressWarnings("all")
public class ArrayBroker {

    /**
     *
     */
    private final Map<String, ArrayMq> arrayMqMap = new ConcurrentHashMap<>(64);

    /**
     * 创建Topic
     *
     * @param topicName
     */
    public void createTopic(String topicName){
        arrayMqMap.putIfAbsent(topicName,new ArrayMq(topicName,ARRAY_CAPACITY));
    }

    /**
     * 创建生产者producer
     *
     * @return ArrayProducer
     */
    public ArrayProducer createProducer(){
        return new ArrayProducer(this);
    }

    /**
     * 创建消费者consumer
     *
     * @return ArrayConsumer
     */
    public ArrayConsumer createConsumer(){
        return new ArrayConsumer(this);
    }

    /**
     * 根据topicName查询对应的ArrayMq
     *
     * @param topicName
     *
     * @return
     */
    public ArrayMq findArrayMq(String topicName){
        return arrayMqMap.get(topicName);
    }

}
