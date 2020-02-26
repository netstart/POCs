package com.github.netstart.scrap.service;

import java.io.IOException;
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.github.netstart.scrap.component.ExchangeAPI;
import com.github.netstart.scrap.component.Rate;
import com.github.netstart.scrap.component.ScrapSmartMei;
import com.github.netstart.scrap.model.ChangePlan;

@Service
public class ChangePlanService {
	@Autowired
	private ScrapSmartMei scrap;

	@Autowired
	private ExchangeAPI exchageAPI;

	public ChangePlanService() throws IOException {
		scrap = new ScrapSmartMei();
		exchageAPI = new ExchangeAPI();
	}

	@Cacheable
	public ChangePlan findProfetionalPlan() {
		BigDecimal brl = scrap.professionalPlan();
		BigDecimal usd = toUSD(brl);
		BigDecimal eur = toEUR(brl);
		return new ChangePlan(scrap.professionalPlanDescription(), brl, usd, eur);
	}

	@Cacheable
	protected BigDecimal toUSD(BigDecimal planValue) {
		Rate rate = exchageAPI.getRateBRL();
		return multiply(rate.usdValue(), planValue);
	}

	@Cacheable
	protected BigDecimal toEUR(BigDecimal planValue) {
		Rate rate = exchageAPI.getRateBRL();
		return multiply(rate.eurValue(), planValue);
	}

	@Cacheable
	protected BigDecimal multiply(BigDecimal brl, BigDecimal usd) {
		return usd.multiply(brl);
	}
}
