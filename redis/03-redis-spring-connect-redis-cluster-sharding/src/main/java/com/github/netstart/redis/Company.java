package com.github.netstart.redis;

import java.io.Serializable;

public class Company implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String CACHE_NAME = "Company";

	private Long id;
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getRedisKey() { 
		return "name:" + getName() + ", id:" +getId();
	}

}
