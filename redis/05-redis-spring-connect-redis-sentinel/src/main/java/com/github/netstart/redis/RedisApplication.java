package com.github.netstart.redis;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * https://github.com/chanjarster/spring-boot-redis-sentinel-example/blob/master/src/main/java/hello/Application.java
 */
@SpringBootApplication
@EnableCaching
public class RedisApplication implements CommandLineRunner {

	@Autowired
	private CacheService cacheService;

	public static void main(String[] args) {
		SpringApplication.run(RedisApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		cacheService.create("Executado na inicializacao: " + new Date().toString());
		while (true) {
			TimeUnit.SECONDS.sleep(10);
			cacheService.set("3SegundosAutomatico:" + new Date().toString());
		}
	}
}
