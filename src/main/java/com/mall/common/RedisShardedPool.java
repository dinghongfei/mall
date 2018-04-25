package com.mall.common;

import com.mall.util.PropertiesUtil;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;

import java.util.ArrayList;
import java.util.List;

/**
 * redis分布式连接池(这里可以称做集群)
 * @author dhf
 */
public class RedisShardedPool {
    private static ShardedJedisPool pool;//sharded jedis连接池
    private static Integer maxTotal;//pool与redis server最大连接数
    private static Integer maxIdle;//最大空闲
    private static Integer minIdle;//最小空闲
    private static boolean testOnBorrow;//在borrow一个jedis实例的时候，是否要进行验证操作，如果赋值为true，则实例是可用
    private static boolean testOnReturn;//在return一个jedis实例的时候，是否要进行验证操作，如果赋值为true，则返回的实例是可用

    private static String ip1;
    private static Integer port1;
    private static String password1;

    private static String ip2;
    private static Integer port2;
    private static String password2;

    private static Integer timeOut;


    static {
        maxTotal = PropertiesUtil.getIntegerPropertry("redis.max.total", 20);
        maxIdle = PropertiesUtil.getIntegerPropertry("redis.max.idle",10 );
        minIdle = PropertiesUtil.getIntegerPropertry("redis.min.idle", 2);
        testOnBorrow = PropertiesUtil.getBooleanPropertry("redis.test.borrow", true);
        testOnReturn = PropertiesUtil.getBooleanPropertry("redis.test.return", false);

        ip1 = PropertiesUtil.getPropertry("redis1.ip");
        port1 =PropertiesUtil.getIntegerPropertry("redis1.port");
        password1 = PropertiesUtil.getPropertry("redis1.password");

        ip2 = PropertiesUtil.getPropertry("redis2.ip");
        port2 =PropertiesUtil.getIntegerPropertry("redis2.port");
        password2 = PropertiesUtil.getPropertry("redis2.password");

        timeOut = PropertiesUtil.getIntegerPropertry("redis.time.out",1000*2);

        initPool();
    }

    private static void initPool(){
        JedisPoolConfig config = new JedisPoolConfig();

        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);

        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);

        config.setBlockWhenExhausted(true);//连接耗尽时，是否阻塞，false抛出异常，true阻塞直到超时，默认为true

        JedisShardInfo info1 = new JedisShardInfo(ip1, port1, timeOut);
        info1.setPassword(password1);
        JedisShardInfo info2 = new JedisShardInfo(ip2, port2, timeOut);
        //info2.setPassword(password2);

        List<JedisShardInfo> jedisShardInfoList = new ArrayList<>(2);
        jedisShardInfoList.add(info1);
        jedisShardInfoList.add(info2);

        pool = new ShardedJedisPool(config, jedisShardInfoList, Hashing.MURMUR_HASH, Sharded.DEFAULT_KEY_TAG_PATTERN);

    }

    public static ShardedJedis getJedis(){
        return pool.getResource();
    }

    public static void returnResource(ShardedJedis jedis){
        pool.returnResource(jedis);
    }
    public static void returnBrokenResource(ShardedJedis jedis){
        pool.returnBrokenResource(jedis);
    }

    public static void main(String[] args) {
        ShardedJedis jedis = pool.getResource();
        for (int i=0;i<10;i++){
            jedis.set("key" + i,"value"+i);
        }
        returnResource(jedis);
        System.out.println("end");


    }




}
