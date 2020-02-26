package com.github.netstart.scrap.component;

import java.io.IOException;
import java.math.BigDecimal;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class ScrapSmartMei {

	private String urlSmartMei = "https://www.smartmei.com.br/";
	private Document doc;

	public ScrapSmartMei() throws IOException {
		doc = Jsoup.connect(urlSmartMei).get();
	}

	@Cacheable
	public String professionalPlanDescription() {
		return doc.getElementsByClass("text-center col-sm-4 col-xs-6 tarifas-2-0-2").text();
	}

	@Cacheable
	public BigDecimal professionalPlan() {
		Elements element = doc.getElementsByClass("text-center col-sm-4 col-xs-6 tarifas-2-2-2");
		return new BigDecimal(element.text().replace("R$", "").trim().replace(",", "."));
	}

}
