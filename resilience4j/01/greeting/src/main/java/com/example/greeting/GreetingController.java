package com.example.greeting;
import com.example.greeting.client.HolidayClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("greetings")
@RequiredArgsConstructor
public class GreetingController {

	private static final String GREETING_SERVER_URL = "http://localhost:8181/holiday-greetings";
	private final HolidayClient holidayClient;

	@GetMapping
	public Mono<String> getGreetings() {
		return holidayClient.getHolidayGreetings(GREETING_SERVER_URL);
	}

	@GetMapping("bulkhead")
	public Mono<String> getGreetingsWithBulkhead() {
		return holidayClient.getHolidayGreetingsWithBulkhead(GREETING_SERVER_URL);
	}

	@GetMapping("rate-limiter")
	public Mono<String> getGreetingsWithRateLimiter() {
		return holidayClient.getHolidayGreetingsWithRateLimiter(GREETING_SERVER_URL);
	}

	@GetMapping("retry")
	public Mono<String> getGreetingsWithRetry() {
		return holidayClient.getHolidayGreetingsWithRetries(GREETING_SERVER_URL);
	}
}