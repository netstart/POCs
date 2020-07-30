package com.github.netstart.redis;

import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.config.MeterFilter;

@Configuration
public class MetricsConfig {
//
//	@Bean
//	MeterRegistryCustomizer<MeterRegistry> meterRegistryCustomizer() {
//		return registry -> registry.config().commonTags("host", "host.clayton", "service", "query-clayton", "region", "region-clayton")
//				.meterFilter(MeterFilter.deny(id -> {
//					String uri = id.getTag("uri");
//					return uri != null && uri.startsWith("/actuator");
//				})).meterFilter(MeterFilter.deny(id -> {
//					String uri = id.getTag("uri");
//					return uri != null && uri.contains("favicon");
//				}));
//	}
}