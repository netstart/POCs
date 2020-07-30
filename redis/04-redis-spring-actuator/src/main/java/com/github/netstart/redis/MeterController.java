package com.github.netstart.redis;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;

/**
 * https://blog.autsoft.hu/defining-custom-metrics-in-a-spring-boot-application-using-micrometer/
 *
 */
@RestController
@RequestMapping("/meter")
public class MeterController {

	@Autowired
	MeterRegistry registry;
	
	@Autowired
	CacheService cacheService;

	@RequestMapping(value = "message/{message}", method = RequestMethod.GET)
	public String getMessage(@PathVariable("message") String message) {

		// counter to count different types of messages received
		registry.counter("custom.metrics.message", "value", message).increment();

		return message;
	}
	
	@RequestMapping(value = "total-bloqueado", method = RequestMethod.GET)
	public void totalBlocked() {
		Gauge
			.builder("protection.scan.key.total.bloqued", ()-> cacheService.totalBloqueado())
			.description("Total of bloqued: ")
			.register(registry);
	}
	
	@RequestMapping(value = "total-query", method = RequestMethod.GET)
	public void totalConsultasDaAplicacaoToda() {
		System.out.println("cacheService.totalConsultasDaAplicacaoToda(): " + cacheService.totalConsultasDaAplicacaoToda());

		// Assim não atualiza em tempo de execução
		 registry.gauge("protection.scan.key.total.query.all.x", cacheService.totalConsultasDaAplicacaoToda());
		 
		// Assim, ao chamar /actuator/prometheus ele irá buscar a informação no cacheService e atualizar o valor
		Gauge
			.builder("protection.scan.key.total.query.all", ()-> cacheService.totalConsultasDaAplicacaoToda())
			.description("Total of key query on the window time: ")
			.register(registry);
	}
	
	@RequestMapping(value = "total-invalid-query", method = RequestMethod.GET)
	public void totalInvalidQuery() {
		Gauge
			.builder("protection.scan.key.total.invalid.query.all", ()->  cacheService.totalConsultasInvalida())
			.description("Total of invalid key query on the window time: ")
			.register(registry);
	}
}