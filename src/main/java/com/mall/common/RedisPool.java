package com.mall.common;

import com.mall.util.PropertiesUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis连接池
 * @author dhf
 */
public class RedisPool {

    private static JedisPool pool;//jedis连接池
    private static Integer maxTotal;//pool与redis server最大连接数
    private static Integer maxIdle;//最大空闲
    private static Integer minIdle;//最小空闲
    private static boolean testOnBorrow;//在borrow一个jedis实例的时候，是否要进行验证操作，如果赋值为true，则实例是可用
    private static boolean testOnReturn;//在return一个jedis实例的时候，是否要进行验证操作，如果赋值为true，则返回的实例是可用

    private static String ip;
    private static Integer port;
    private static String password;
    private static Integer timeOut;


    static {
        maxTotal = PropertiesUtil.getIntegerPropertry("redis.max.total", 20);
        maxIdle = PropertiesUtil.getIntegerPropertry("redis.max.idle",10 );
        minIdle = PropertiesUtil.getIntegerPropertry("redis.min.idle", 2);
        testOnBorrow = PropertiesUtil.getBooleanPropertry("redis.test.borrow", true);
        testOnReturn = PropertiesUtil.getBooleanPropertry("redis.test.return", false);

        ip = PropertiesUtil.getPropertry("redis1.ip");
        port =PropertiesUtil.getIntegerPropertry("redis1.port");
        password = PropertiesUtil.getPropertry("redis1.password");
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

        pool = new JedisPool(config,ip,port,timeOut,password);
    }

    public static Jedis getJedis(){
        return pool.getResource();
    }

    public static void returnResource(Jedis jedis){
        pool.returnResource(jedis);
    }
    public static void returnBrokenResource(Jedis jedis){
        pool.returnBrokenResource(jedis);
    }


    public static void main(String[] args) {
        Jedis jedis = pool.getResource();
        jedis.select(2);
        jedis.set("testkey", "testvalue");

        returnResource(jedis);

        pool.destroy();//临时调用，销毁连接池中所有连接

        System.out.println("end");
    }




}
