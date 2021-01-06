package com.tonyxu.redis.demo.constant;

/**
 * @author Administrator
 * @date 2021-01-06 15:12
 */
public class DemoConstants {
    /**
     * 默认锁的名字
     */
    public static final String DEFAULT_REDIS_LOCK = "lock";

    /**
     * 锁默认失效时间，单位秒
     */
    public static final int DEFAULT_EXPIRE_SECONDS = 10;

    /**
     * 锁定脚本
     */
    public static final String LOCK_LUA_SCRIPT_PATH = "script/lock.lua";

    /**
     * 释放锁脚本
     */
    public static final String RELEASE_LOCK_LUA_SCRIPT_PATH = "script/release_lock.lua";

    /**
     * 计数器的名字
     */
    public static final String REDIS_COUNTER_KEY = "counter";
}
