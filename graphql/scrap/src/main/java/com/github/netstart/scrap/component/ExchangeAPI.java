package com.github.netstart.scrap.component;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ExchangeAPI {
	private static final String urlAPI = "https://api.exchangeratesapi.io/latest?base=BRL";

	private RestTemplate restTemplate;

	public ExchangeAPI() {
		restTemplate = new RestTemplate();
	}

	public Rate getRateBRL() {
		return rate(urlAPI).getBody();
	}

	@Cacheable
	protected ResponseEntity<Rate> rate(String urlAPI) {
		return restTemplate.getForEntity(urlAPI, Rate.class);
	}

}
