package com.github.netstart.redis;

import java.util.Date;

import redis.clients.jedis.Jedis;

public class MainJedis {
	public static void main(String[] args) {
		
		Jedis jedis = new Jedis("localhost");
		
		jedis.setex("query-key:clayton@gmail.com:" + new Date().getTime(), 30, "4430409900");
		jedis.setex("query-key:clayton@gmail.com:" + new Date().getTime(), 30, "4430409900");
		System.out.println(jedis.keys("query-key:clayton@gmail.com:*"));
	}
}
