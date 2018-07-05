package com.wizz.seckill.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class RedisService {

    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    public void setCnt(String key, Long val){
        RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        entityIdCounter.set(val);
    }

    public Long incrCnt(String key){
        RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        return entityIdCounter.getAndIncrement();
    }

    public Long increment(String key){
        return redisTemplate.opsForValue().increment(key,1);
    }


    public Long getCnt(String key){
        RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        return entityIdCounter.get();

    }

    public void delete(String key) {
       redisTemplate.opsForValue().getOperations().delete(key);
    }

    /**
     * 实现命令：SET key value EX seconds，设置key-value和超时时间（秒）
     *
     * @param key
     * @param value
     * @param timeout
     *            （以秒为单位）
     */
    public void set(String key, String value, long timeout) {
        //redisTemplate.opsForValue().
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    public void set(String key,String value){
        redisTemplate.opsForValue().set(key, value);
    }

    public Object get(String key){
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 实现命令：TTL key，以秒为单位，返回给定 key的剩余生存时间(TTL, time to live)。
     *
     * @param key
     * @return
     */
    public long ttl(String key) {
        return redisTemplate.getExpire(key);
    }
}