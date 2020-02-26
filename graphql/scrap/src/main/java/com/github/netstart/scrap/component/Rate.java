package com.github.netstart.scrap.component;

import java.math.BigDecimal;
import java.util.Map;

public class Rate {
	private Map<String, String> rates;

	public final static String USD = "USD";
	public final static String EUR = "EUR";

	protected Map<String, String> getRates() {
		return rates;
	}

	protected void setRates(Map<String, String> rates) {
		this.rates = rates;
	}

	public String usd() {
		return get(USD);
	}

	public BigDecimal usdValue() {
		return new BigDecimal(usd());
	}

	public String eur() {
		return get(EUR);
	}

	public BigDecimal eurValue() {
		return new BigDecimal(eur());
	}

	public String get(String key) {
		return this.rates.get(key);
	}

}
