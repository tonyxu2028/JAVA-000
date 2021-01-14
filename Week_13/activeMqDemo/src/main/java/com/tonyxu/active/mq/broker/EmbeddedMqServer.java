package com.tonyxu.active.mq.broker;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 创建内嵌 MQ server
 *
 * Created on 2021/1/14.
 *
 * @author <a href="191284969@qq.com">Tony xu</a>
 *
 * @see <a href="http://activemq.apache.org/how-do-i-embed-a-broker-inside-a-connection.html">How do I embed a Broker inside a Connection.</a>
 */
public class EmbeddedMqServer {

    /**
     * 使用 ActiveMQConnectionFactory 创建
     *
     * @return ActiveMQConnectionFactory
     */
    public static ActiveMQConnectionFactory createServerByActiveMqConnectionFactory() {
        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory(
                "vm://localhost?broker.persistent=false");
        return cf;
    }
}
