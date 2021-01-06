package com.tonyxu.redis.demo.service;

import com.tonyxu.redis.demo.domain.RedisCounter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author Tony.xu
 * @date 2021-01-06 14:56
 */
@Service
@Slf4j
public class DemoServiceImpl implements DemoService{
    /**
     * 商品库存缓存初始值
     */
    private static final int INVENTORY_AMOUNT = 100;

    private RedisCounter redisCounter;

    /**
     * 初始化 Redis 商品库存
     */
    @PostConstruct
    private void init() {
        log.debug("初始化商品库存，库存数量：[{}]", INVENTORY_AMOUNT);
        this.redisCounter = new RedisCounter(INVENTORY_AMOUNT, "127.0.0.1", 6379);
    }

    @Override
    public String decreaseInventory() {
        int idleInventory = redisCounter.decreaseAndGet();
        log.debug("剩余库存数量：{}", idleInventory);
        if (idleInventory < 0) {
            log.debug("No inventory!");
            return "No inventory!";
        } else {
            log.debug("success");
            return "success";
        }
    }
}
