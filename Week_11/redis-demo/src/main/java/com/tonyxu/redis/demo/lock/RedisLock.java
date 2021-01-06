package com.tonyxu.redis.demo.lock;

import com.tonyxu.redis.demo.constant.DemoConstants;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * Redis的分布式锁
 * @author Tony.xu
 * @date 2021-01-05 18:12
 */
public class RedisLock {

    /**
     * 获取锁脚本
     */
    public DefaultRedisScript<Long> lockScript;

    /**
     * 释放锁脚本
     */
    public DefaultRedisScript<Long> releaseLockScript;

    /**
     * Redis模板
     */
    private StringRedisTemplate redisTemplate;

    public RedisLock(String host, int port) {
        init(host, port);
    }

    /**
     * Redis初始化,并且获取锁定脚本和锁定释放脚本
     * @param host
     * @param port
     */
    private void init(String host, int port) {
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(host, port);
        lettuceConnectionFactory.afterPropertiesSet();
        this.redisTemplate = new StringRedisTemplate(lettuceConnectionFactory);
        this.redisTemplate.afterPropertiesSet();
        //初始化锁定脚本
        initLockScript();
        //初始化释放脚本
        initReleaseLockScript();
    }

    private void initLockScript() {
        this.lockScript = new DefaultRedisScript<>();
        this.lockScript.setResultType(Long.class);
        this.lockScript.setScriptSource(
                new ResourceScriptSource(
                        new ClassPathResource(
                                DemoConstants.LOCK_LUA_SCRIPT_PATH)));
    }

    private void initReleaseLockScript() {
        this.releaseLockScript = new DefaultRedisScript<>();
        this.releaseLockScript.setResultType(Long.class);
        this.releaseLockScript.setScriptSource(
                new ResourceScriptSource(
                        new ClassPathResource(
                        DemoConstants.RELEASE_LOCK_LUA_SCRIPT_PATH)));
    }

    /**
     * 获取锁lock
     */
    public void lock() {
        boolean isLocked = isLocked();
        while (isLocked) {
            isLocked = isLocked();
        }
    }

    /**
     * 获取锁lockWithLua
     */
    public void lockWithLua() {
        boolean isLocked = isLockedWithLua();
        while (isLocked) {
            isLocked = isLockedWithLua();
        }
    }

    /**
     * 判断是否锁定
     * @return
     */
    private boolean isLocked() {
        ValueOperations<String, String> valueOperations = this.redisTemplate.opsForValue();
        return !valueOperations.setIfAbsent(
                DemoConstants.DEFAULT_REDIS_LOCK,
                DemoConstants.DEFAULT_REDIS_LOCK,
                DemoConstants.DEFAULT_EXPIRE_SECONDS,
                TimeUnit.SECONDS);
    }

    /**
     * 判断是否锁定LockedWithLua
     * @return
     */
    private boolean isLockedWithLua() {
        Long execute = this.redisTemplate.execute(lockScript, Collections.singletonList(
                DemoConstants.DEFAULT_REDIS_LOCK),
                DemoConstants.DEFAULT_REDIS_LOCK,
                String.valueOf(DemoConstants.DEFAULT_EXPIRE_SECONDS));
        return 0 == execute.intValue();
    }

    /**
     * 释放锁
     */
    public void unlock() {
        this.redisTemplate.execute(releaseLockScript, Collections.singletonList(
                DemoConstants.DEFAULT_REDIS_LOCK),
                DemoConstants.DEFAULT_REDIS_LOCK);
    }

}