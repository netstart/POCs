package com.github.netstart.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisConfig {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
}
