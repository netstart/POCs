package com.github.netstart.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;


@Configuration
public class MeterConfig {

	@Autowired
	MeterRegistry registry;
	
	@Autowired
	CacheService cacheService;

	@Bean("GaugeTotalBloqued")
	public Gauge totalBlocked() {
		return Gauge
			.builder("protection.scan.key.total.bloqued", ()-> cacheService.totalBloqueado())
			.description("Total of bloqued: ")
			.register(registry);
	}
	
	@Bean("TotalQueryAll")
	public Gauge totalConsultasDaAplicacaoToda() {
		// Assim não atualiza em tempo de execução
		// registry.gauge("protection.scan.key.total.query.all.x", cacheService.totalConsultasDaAplicacaoToda());
		 
		/* 
		 * Assim, ao chamar /actuator/prometheus ele irá buscar a informação no cacheService e atualizar o valor
		 * 
		 * Para não ir sempre no Redis, e acabar se tornando lento, 
		 * é legal usar @Cacheble ou outro mecanismo de cache de armazenamento local, 
		 * que expira de tempos em tempos.
		 */
		return Gauge
			.builder("protection.scan.key.total.query.all", ()-> cacheService.totalConsultasDaAplicacaoToda())
			.description("Total of key query on the window time: ")
			.register(registry);
	}
	
	@Bean("TotalInvalidQuery")
	public Gauge totalInvalidQuery() {
		return Gauge
			.builder("protection.scan.key.total.invalid.query.all", ()->  cacheService.totalConsultasInvalida())
			.description("Total of invalid key query on the window time: ")
			.register(registry);
	}
}