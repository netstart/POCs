package com.github.netstart.redis;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class CacheService {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	public void create(String key) {
		// Cria o registro com ttl de 30 segundos
		redisTemplate.opsForValue().set("query-key:clayton@gmail.com:" + new Date().getTime(), key, 1, TimeUnit.MINUTES);
		redisTemplate.opsForValue().set("query-key:clayton@gmail.com:" + new Date().getTime(), key, 30, TimeUnit.SECONDS);
		
		redisTemplate.opsForValue().set("query-key:clayton@gmail.com:112365324", key, 30, TimeUnit.SECONDS);
	}

	public Integer info() {
		System.out.println("============");
		System.out.println("get: " + redisTemplate.opsForValue().get("query-key:clayton@gmail.com:112365324"));
		System.out.println("ttl: " + redisTemplate.getExpire("query-key:clayton@gmail.com:112365324"));

		// Quantos registros tem com a chave? Asterisco é caracter coringa que quer dizer qualquer coisa
		System.out.println("keys: " + redisTemplate.keys("query-key:clayton@gmail.com:*"));
		System.out.println("keys size: " + redisTemplate.keys("query-key:clayton@gmail.com:*").size());
		
		
		redisTemplate.delete("query-key:clayton@gmail.com:112365324");
		System.out.println("keys size after delete: " + redisTemplate.keys("query-key:clayton@gmail.com:*").size());
		
		redisTemplate.delete("query-key:clayton@gmail.com:*");
		System.out.println("keys size after delete * : " + redisTemplate.keys("query-key:clayton@gmail.com:*").size());
		
		return redisTemplate.keys("query-key:clayton@gmail.com:*").size();
	}
	
}
