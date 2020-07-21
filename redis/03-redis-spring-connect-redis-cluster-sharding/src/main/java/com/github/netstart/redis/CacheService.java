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

	public void create(String value) {
		// Cria o registro com ttl de 30 segundos
		redisTemplate.opsForValue().set("query-key:clayton@gmail.com:" + new Date().getTime(), value, 10, TimeUnit.MINUTES);
		System.out.println("keys size 1: " + redisTemplate.keys("query-key:*").size());
		System.out.println("keys array size 1: " + redisTemplate.keys("query-key:*"));
		
		redisTemplate.opsForValue().set("query-key:clayton@gmail.com:" + new Date().getTime(), value, 30, TimeUnit.MINUTES);
		System.out.println("keys size 2: " + redisTemplate.keys("*").size());
		System.out.println("keys array size 2: " + redisTemplate.keys("query-key:*"));	
		
		redisTemplate.opsForValue().set("query-key:clayton@gmail.com:112365324", value, 30, TimeUnit.MINUTES);
		System.out.println("keys size 3: " + redisTemplate.keys("*").size());
		System.out.println("keys array size 3: " + redisTemplate.keys("query-key:*"));
		
		redisTemplate.opsForValue().set("XPTO", "¯\\_(ツ)_/¯", 10, TimeUnit.MINUTES);
		System.out.println("keys size 4: " + redisTemplate.keys("*").size());
		System.out.println("keys array size 4: " + redisTemplate.keys("*"));
		
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

		System.out.println("keys XPTO: " + redisTemplate.keys("XPTO*"));
		
		return redisTemplate.keys("query-key:clayton@gmail.com:*").size();
	}
	
}