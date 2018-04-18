package com.mall.util;

import com.mall.common.RedisShardedPool;
import com.mall.common.RedisShardedPool;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;

/**
 * @author dhf
 */
@Slf4j
public class RedisShardedPoolUtil {

    /**
     * 设置key的有效期，单位是秒
     * @param key
     * @param exTime
     * @return
     */
    public static Long expire(String key,int exTime){
        ShardedJedis jedis = null;
        Long result = null;
        try {
            jedis = RedisShardedPool.getJedis();
            result = jedis.expire(key, exTime);
        } catch (Exception e){
            log.error("expire key:{} error:{}",key,e);
            RedisShardedPool.returnBrokenResource(jedis);
            return result;
        }
        RedisShardedPool.returnResource(jedis);
        return result;

    }

    /**
     * 设置-同时设置有效期
     * @param key       key
     * @param value     value
     * @param exTime    时间
     * @return          string
     */
    //exTime单位为秒
    public static String setEx(String key,String value,int exTime){
        ShardedJedis jedis = null;
        String result = null;

        try {
            jedis = RedisShardedPool.getJedis();
            result = jedis.setex(key, exTime,value);
        } catch (Exception e){
            log.error("setex key:{} value:{} error:{}",key,value,e);
            RedisShardedPool.returnBrokenResource(jedis);
            return result;
        }
        RedisShardedPool.returnResource(jedis);
        return result;
    }

    /**
     * 设置key-value
     * @param key   key
     * @param value value
     * @return      String
     */
    public static String set(String key,String value){
        ShardedJedis jedis = null;
        String result = null;

        try {
            jedis = RedisShardedPool.getJedis();
            result = jedis.set(key,value);
        } catch (Exception e){
            log.error("set key:{} value:{} error:{}",key,value,e);
            RedisShardedPool.returnBrokenResource(jedis);
            return result;
        }
        RedisShardedPool.returnResource(jedis);
        return result;
    }

    /**
     * 根据key获取
     * @param key   key
     * @return      value
     */
    public static String get(String key){
        ShardedJedis jedis = null;
        String result = null;
        try {
            jedis = RedisShardedPool.getJedis();
            result = jedis.get(key);
        } catch (Exception e){
            log.error("get key:{} error:{}",key,e);
            RedisShardedPool.returnBrokenResource(jedis);
            return result;
        }
        RedisShardedPool.returnResource(jedis);
        return result;
    }

    /**
     * 删除
     * @param key   key
     * @return      Long
     */
    public static Long del(String key){
        ShardedJedis jedis = null;
        Long result = null;
        try {
            jedis = RedisShardedPool.getJedis();
            result = jedis.del(key);
        } catch (Exception e){
            log.error("del key:{} error:{}",key,e);
            RedisShardedPool.returnBrokenResource(jedis);
            return result;
        }
        RedisShardedPool.returnResource(jedis);
        return result;
    }

    public static void main(String[] args) {
        System.out.println(RedisShardedPoolUtil.set("keytest", "valuetest"));
        System.out.println(RedisShardedPoolUtil.get("keytest"));
        System.out.println(RedisShardedPoolUtil.setEx("keyex","valueex",60*10));
        System.out.println(RedisShardedPoolUtil.expire("keytest",60*20));

        System.out.println(RedisShardedPoolUtil.del("keytest"));

    }



}
