package com.github.netstart.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company/cache/")
public class CompanyCacheServiceController {

	@Autowired
	private CacheService cacheService;

	@PostMapping("/{key}")
	public void create(@PathVariable("key") final String key) {
		cacheService.create(key);
	}
	
    @GetMapping("/")
    public Integer info() {
        return cacheService.info();
    }

}
