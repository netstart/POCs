package com.github.netstart.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

@Component
public class CustomHealthIndicator extends AbstractHealthIndicator {

	@Autowired
	private CacheService cacheService;

	@Override
	protected void doHealthCheck(Health.Builder builder) throws Exception {
		builder
			.withDetail("totalBloqueado", cacheService.totalBloqueado())
			.withDetail("totalConsultasInvalida", cacheService.totalConsultasInvalida())
			.withDetail("totalDaAplicacaoToda", cacheService.totalDaAplicacaoToda())
			.down();
	}
}