package com.github.netstart.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

/**
 * Acessar: http://localhost:8080/actuator/health
 * 
 * A parte antes do HealthIndicator do nome da classe vai fazer parte da chave no json, 
 *
 */
@Component
public class IndicadorTotalConsultasHealthIndicator extends AbstractHealthIndicator {

	@Autowired
	private CacheService cacheService;

	@Override
	protected void doHealthCheck(Health.Builder builder) throws Exception {
		builder
			.withDetail("totalBloqueado", cacheService.totalBloqueado())
			.withDetail("totalConsultasInvalida", cacheService.totalConsultasInvalida())
			.withDetail("totalConsultasDaAplicacaoToda", cacheService.totalConsultasDaAplicacaoToda())
			.up();
	}
}