package com.cdz.netty.rediscache;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: CDz
 * Create: 2020-04-20 23:08
 **/
public class JedisConfig {

    public static JedisPool JEDIS_POOL = new JedisPool("localhost",6379);


    public static void main(String[] args) {
        Jedis resource = JEDIS_POOL.getResource();
        resource.set("key","value");

        System.out.println(resource.get("key"));
    }
}
