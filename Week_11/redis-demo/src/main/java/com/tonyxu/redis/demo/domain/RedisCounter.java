package com.tonyxu.redis.demo.domain;

import com.tonyxu.redis.demo.constant.DemoConstants;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Redis 计数器
 *
 * @author Tony.xu
 * 2021/1/5
 */
public class RedisCounter {



    private StringRedisTemplate redisTemplate;

    public RedisCounter(int initialValue, String host, int port) {
        init(initialValue, host, port);
    }

    private void init(int initialValue, String host, int port) {
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(host, port);
        lettuceConnectionFactory.afterPropertiesSet();
        this.redisTemplate = new StringRedisTemplate(lettuceConnectionFactory);
        this.redisTemplate.afterPropertiesSet();
        this.redisTemplate.opsForValue().setIfAbsent(DemoConstants.REDIS_COUNTER_KEY, String.valueOf(initialValue));
    }

    /**
     * 计数器减一，并返回减小后的值
     *
     * @return 减小后的值
     */
    public int decreaseAndGet() {
        return this.redisTemplate.opsForValue().decrement(DemoConstants.REDIS_COUNTER_KEY).intValue();
    }

    /**
     * 计数器加一，并返回增加后的值
     *
     * @return 增加后的值
     */
    public int increaseAndGet() {
        return this.redisTemplate.opsForValue()
                .increment(DemoConstants.REDIS_COUNTER_KEY).intValue();
    }

    /**
     * 获取计数器当前的值
     *
     * @return 计数器当前的值
     */
    public int getCurrent() {
        return Integer.parseInt(this.redisTemplate.opsForValue()
                .get(DemoConstants.REDIS_COUNTER_KEY));
    }

    /**
     * 重置计数器
     *
     * @param initialValue 计数器初始值
     */
    public void reset(int initialValue) {
        this.redisTemplate.opsForValue().set(DemoConstants.REDIS_COUNTER_KEY, String.valueOf(initialValue));
    }

}
