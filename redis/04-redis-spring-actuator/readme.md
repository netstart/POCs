

Demontra como usar o Redis como cache
Neste caso o Cache também é conhecido em outros ecosistems como memoize

docker run -it \
    --name redis \
    -p 6379:6379 \
    redis:5.0.3
    
Para que as informações sejam armazenadas no cache com @Cacheble no Redis, é necessário dizer ao Spring que deve armazenar no Redis incluindo no `application.properties`
 
```
spring.cache.type=redis
```


#### actuator/health
A classe IndicadorTotalConsultasHealthIndicator configura e disponibiliza informações no: http://localhost:8080/actuator/health



#### Prometheus (ver classe MeterConfig)



Ao executar 
`
curl -s -X POST http://localhost:8080/company/cache/1010 -H "Content-Type: application/json"
`

Vai criar novos registros, que podem ser acompanhados no prometheus: http://localhost:8080/actuator/prometheus

E podem ser procurados na página por:

- protection_scan_key_total_query_all
- protection_scan_key_total_bloqued
- protection_scan_key_total_invalid_query_all

Executa o método info
curl -s http://localhost:8080/company/cache/ -H "Content-Type: application/json"







### Packs supported of Meter https://docs.spring.io/spring-metrics/docs/current/public/prometheus

https://micrometer.io/docs/concepts


A meter is the interface for collecting a set of measurements (which we individually call metrics) about your application. spring-metrics packs with a supported set of Meter primitives including: Timer, Counter, Gauge, DistributionSummary, and LongTaskTimer. Note that different meter types result in a different number of metrics. For example, while there is a single metric that represents a Gauge, a Timer measures both number of timed events and the total time of all events timed.

A registry creates and manages your application's set of meters. Exporters use the meter registry to iterate over the set of meters instrumenting your application, and then further iterate over each meter's metrics, generally resulting in a time series in the metrics backend for each combination of metrics and dimensions.

##### Timer
A Counter tracks a value that can increase. 

Timers are useful for measuring short-duration latencies and the frequency of such events. All implementations of Timer report at least the total time and count of events as separate time series.

As an example, consider a graph showing request latency to a typical web server. The server can be expected to respond to many requests quickly, so the timer will be getting updated many times per second.

The appropriate base unit for timers does vary by metrics backend for good reason. Prometheus recommends recording timings in seconds (as this is technically a base unit), but records this value as a double. spring-metrics is decidedly un-opinionated about this, but because of the potential for confusion, requires a TimeUnit when interacting with Timers. spring-metrics is aware of the preferences of each implementation and stores your timing in the appropriate base unit based on the implementation.

#### Gauges
A Gauge measures and returns the observed value when the meter is published (or queried).

Gauge gives us instantaneous data like the length of a queue

A gauge is a handle to get the current value. Typical examples for gauges would be the size of a collection or map or number of threads in a running state.

spring-metrics takes the stance that gauges should be sampled and not set, so there is no information about what might have occurred between samples. After all, any intermediate values set on a gauge are lost by the time the gauge value is reported to a metrics backend anyway, so there seems to be little value in setting those intermediate values in the first place.

If it helps, think of a Gauge as a heisengauge - a meter that only changes when it is observed.

The MeterRegistry interface contains a number of convenience methods for instrumenting collections, maps, executors, and caches with gauges.

Lastly, Gauges are useful for monitoring things with natural upper bounds. We don't recommend using a gauge to monitor things like request count, as they can grow without bound for the duration of an application instance's life.

In Prometheus, a gauge is a generalization of a counter that also happens to allow for decrementing. If you view a gauge as something that is actively set by the application application code rather than sampled, it is clear that your code would have to increment and decrement the gauge as the size of the thing being measured changes. Diligent incrementing and decrementing throughout the application code yields the same result as the heisengauge, ultimately.



- A Timer tracks how many times an event occurs, and the cumulative elapsed time for that event.
- A DistributionSummary is similar to a Timer, but it also tracks a distribution of events, and does not measure time.


