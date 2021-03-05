```
mvn clean package -DskipTests
./mvnw spring-boot:run
```

### Features surported

- [x] mTLS
- [x] Interceptors - HttpRequestInterceptor
- [x] Spring RestTemplate interceptor to log request and response
- [x] Connection pool with PoolingHttpClientConnectionManager
- [ ] Housekeeping connection pool - Connection eviction policy - idle And Expired Connection close
- [ ] Keep alive strategy
  - https://www.dhaval-shah.com/rest-client-with-desired-nfrs-using-springs-resttemplate/
  - https://github.com/dhaval201279/RESTClientDemo
- [x] Max connection total
- [x] Max connection per route
- [x] Connection time to live
- [x] Gzip disable
- [x] Timeout
- [ ] Reload trust store and key store on the fly
- [x] Retry using Http Apache class DefaultHttpRequestRetryHandler
- [x] Retry Service Unavailable using DefaultServiceUnavailableRetryStrategy
- [ ] Spring @Retry or @Recover - https://www.dhaval-shah.com/rest-client-with-desired-nfrs-using-springs-resttemplate/


### Properies
- `server.ssl.key-store=classpath:server-nonprod.jks` - The path to the key store that contains the SSL certificate. In our example, we want Spring Boot to look for it in the classpath.
- `server.ssl.key-store-password=changeme` - The password used to access the key store.
- `server.ssl.key-store-type:` - The type of the key store (JKS or PKCS12).
- `server.ssl.key-alias:` - The alias that identifies the key in the key store.
- `server.ssl.key-password:` - The password used to access the key in the key store.
- `server.ssl.client-auth=need` - The 'client-auth' property specifies whether client authentication is wanted (“want”) or needed (“need”).
  In this example we set it to 'need' as we want to assure two-way SSL is established.
  The server’s truststore and the corresponding password are also configured so that the public certificate of the client is trusted.


### Referênces:
- https://github.com/joutwate/mtls-springboot

- https://codenotfound.com/spring-ws-mutual-authentication-example.html
- https://dzone.com/articles/hakky54mutual-tls-1
- https://www.dhaval-shah.com/rest-client-with-desired-nfrs-using-springs-resttemplate/

#### Make SSL certification
- https://www.opencodez.com/java/implement-2-way-authentication-using-ssl.htm
- https://dzone.com/articles/hakky54mutual-tls-1
- https://medium.com/@niral22/2-way-ssl-with-spring-boot-microservices-2c97c974e83
- https://livebook.manning.com/book/microservices-security-in-action/chapter-6/v-3/18
- https://codenotfound.com/spring-ws-mutual-authentication-example.html

#### Spring
- https://medium.com/tech-grupozap/pensando-melhor-sobre-conex%C3%B5es-http-e7105ccf8b56
- https://www.thomasvitale.com/https-spring-boot-ssl-certificate/

#### Spring properties:
- https://docs.spring.io/spring-boot/docs/current/reference/html/appendix-application-properties.html#server.ssl.ciphers



Housekeeping connection pool 
  - https://www.dhaval-shah.com/rest-client-with-desired-nfrs-using-springs-resttemplate/
  - https://github.com/dhaval201279/RESTClientDemo
  - https://hc.apache.org/httpcomponents-client-4.5.x/current/tutorial/html/connmgmt.html#d5e418
```
    /**
     * To support @Scheduled annotation in HttpClientConfig, we have to add support of scheduled
     * execution of thread. For that, we have used bean ThreadPoolTaskScheduler which internally
     * utilizes ScheduledThreadPoolExecutor to schedule commands to run after a given delay, or to
     * execute periodically.
     * */
    @Bean
    public TaskScheduler taskScheduler() {
        log.info("Entering taskScheduler");
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setThreadNamePrefix("threadPoolScheduler");
        scheduler.setPoolSize(50);
        log.info("Leaving taskScheduler");
        return scheduler;
    }
    
     /**
     * a dedicated monitor thread used to evict connections that are considered expired due to a long
     * period of inactivity. The monitor thread can periodically call
     * ClientConnectionManager#closeExpiredConnections() method to close all expired connections and
     * evict closed connections from the pool. It can also optionally call
     * ClientConnectionManager#closeIdleConnections() method to close all connections that have been
     * idle over a given period of time.
     * */
    @Bean
    public Runnable idleAndExpiredConnectionProcessor(final PoolingHttpClientConnectionManager connectionManager) {
        return new Runnable() {
            @Override
            @Scheduled(fixedDelay = 20000)
            public void run() {
                log.info("Entering idleAndExpiredConnectionProcessor");
                try {
                    if (connectionManager != null) {
                        log.info("Closing expired and idle connections...");
                        connectionManager.closeExpiredConnections();
                        connectionManager.closeIdleConnections(IDLE_CONNECTION_WAIT_TIME_SECS, TimeUnit.SECONDS);
                    } else {
                        log.info("Http Client Connection manager has not been initialised");
                    }
                } catch (Exception e) {
                    log.error("Exception occurred whilst closing expired and idel connections. msg = {}, e = {}", e.getMessage(), e);
                }
                log.info("Leaving idleAndExpiredConnectionProcessor");
            }
        };
    }

    /**
     * Routes -
     * PoolingHttpClientConnectionManager maintains a maximum limit of connections on a per route
     * basis and in total. Per default this implementation will create no more than 2 concurrent
     * connections per given route and no more 20 connections in total. For many real-world applications
     * these limits may prove too constraining, especially if they use HTTP as a transport protocol
     * for their services.
     *
     *
     * */
    @Bean
    public Runnable connectionPoolMetricsLogger(final PoolingHttpClientConnectionManager connectionManager) {
        return new Runnable() {
            @Override
            @Scheduled(fixedDelay = 30000)
            public void run() {
                log.info("Entering connectionPoolMetricsLogger");
                final StringBuilder buffer = new StringBuilder();
                try {
                    if (connectionManager != null) {
                        final PoolStats totalPoolStats = connectionManager.getTotalStats();
                        log.info(" ** HTTP Client Connection Pool Stats : Available = {}, Leased = {}, Pending = {}, Max = {} **",
                                totalPoolStats.getAvailable(), totalPoolStats.getLeased(), totalPoolStats.getPending(), totalPoolStats.getMax());

                        connectionManager
                            .getRoutes()
                            .stream()
                            .forEach(route -> {
                                final PoolStats routeStats = connectionManager.getStats(route);
                                buffer
                                        .append(" ++ HTTP Client Connection Pool Route Pool Stats ++ ")
                                        .append(" Route : " + route.toString())
                                        .append(" Available : " + routeStats.getAvailable())
                                        .append(" Leased : " + routeStats.getLeased())
                                        .append(" Pending : " + routeStats.getPending())
                                        .append(" Max : " + routeStats.getMax());
                            });
                        log.info(buffer.toString());
                    } else {
                        log.info("Http Client Connection manager has not been initialised");
                    }
                } catch (Exception e) {
                    log.error("Exception occurred whilst logging http connection pool stats. msg = {}, e = {}", e.getMessage(), e);
                }
                log.info("Leaving connectionPoolMetricsLogger");
            }
        };
    }
    
    
    
    /**
     *  If the Keep-Alive header is not present in the response, HttpClient assumes the connection can be
     *  kept alive indefinitely. However, many HTTP servers in general use are configured to drop
     *  persistent connections after a certain period of inactivity in order to conserve system
     *  resources, quite often without informing the client. In case the default strategy turns out
     *  to be too optimistic, one may want to provide a custom keep-alive strategy.
     *
     * */
    private ConnectionKeepAliveStrategy connectionKeepAliveStrategy() {
        return new DefaultConnectionKeepAliveStrategy() {
            @Override
            public long getKeepAliveDuration(final HttpResponse response, final HttpContext context) {
                log.info("Instantiating DefaultConnectionKeepAliveStrategy by determining keep alive time ");
                long keepAliveDuration = super.getKeepAliveDuration(response, context);

                if (keepAliveDuration < 0) {
                    keepAliveDuration = DEFAULT_KEEP_ALIVE_TIME_MILLIS;
                } else if (keepAliveDuration > MAX_KEEP_ALIVE_TIME_MILLIS) {
                    keepAliveDuration = MAX_KEEP_ALIVE_TIME_MILLIS;
                }
                log.info("Keep alive duration = {} ", keepAliveDuration);
                return keepAliveDuration;
            }
        };
    }
```