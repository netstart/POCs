```
mvn clean package -DskipTests
./mvnw spring-boot:run
```

### Features surported

- [x] mTLS
- [x] Interceptors - HttpRequestInterceptor
- [ ] Connection pool
- [x] Max connection total
- [x] Max connection per route
- [x] Connection time to live
- [x] Gzip disable
- [x] Timeout
- [ ] Reload trust store and key store on the fly
- [x] Retry using Http Apache class DefaultHttpRequestRetryHandler
- [x] Retry Service Unavailable using DefaultServiceUnavailableRetryStrategy
    - [ ] Retry Service Unavaiable
    

### Referênces: 
- https://github.com/joutwate/mtls-springboot
- https://codenotfound.com/spring-ws-mutual-authentication-example.html
- https://dzone.com/articles/hakky54mutual-tls-1
- https://www.opencodez.com/java/implement-2-way-authentication-using-ssl.htm
- https://livebook.manning.com/book/microservices-security-in-action/chapter-6/v-3/1
- https://www.dhaval-shah.com/rest-client-with-desired-nfrs-using-springs-resttemplate/
- https://medium.com/tech-grupozap/pensando-melhor-sobre-conex%C3%B5es-http-e7105ccf8b56

