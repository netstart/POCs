package com.github.netstart.scrap.model;

import java.math.BigDecimal;
import java.util.Date;

public class ChangePlan {

	private Date now;
	private String description;
	private BigDecimal brl;
	private BigDecimal usd;
	private BigDecimal eur;

	public ChangePlan(String description, BigDecimal brl, BigDecimal usd, BigDecimal eur) {
		this.now = new Date();
		this.description = description;
		this.brl = brl;
		this.usd = usd;
		this.eur = eur;
	}

	public Date getNow() {
		return now;
	}

	public void setNow(Date now) {
		this.now = now;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getBrl() {
		return brl;
	}

	public void setBrl(BigDecimal brl) {
		this.brl = brl;
	}

	public BigDecimal getUsd() {
		return usd;
	}

	public void setUsd(BigDecimal usd) {
		this.usd = usd;
	}

	public BigDecimal getEur() {
		return eur;
	}

	public void setEur(BigDecimal eur) {
		this.eur = eur;
	}

}
