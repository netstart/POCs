```
mvn clean package -DskipTests
./mvnw spring-boot:run
```

### Features surported

- [x] mTLS
- [x] Interceptors - HttpRequestInterceptor
- [x] Spring RestTemplate interceptor to log request and response
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


### Properies
- `server.ssl.key-store=classpath:server-nonprod.jks` - The path to the key store that contains the SSL certificate. In our example, we want Spring Boot to look for it in the classpath.
- `server.ssl.key-store-password=changeme` - The password used to access the key store.
- `server.ssl.key-store-type:` - The type of the key store (JKS or PKCS12).
- `server.ssl.key-alias:` - The alias that identifies the key in the key store.
- `server.ssl.key-password:` - The password used to access the key in the key store.
- `server.ssl.client-auth=need` - The 'client-auth' property specifies whether client authentication is wanted (“want”) or needed (“need”).
  In this example we set it to 'need' as we want to assure two-way SSL is established.
  The server’s truststore and the corresponding password are also configured so that the public certificate of the client is trusted.

#### Spring properties:
- https://docs.spring.io/spring-boot/docs/current/reference/html/appendix-application-properties.html#server.ssl.ciphers



### Referênces:


- https://github.com/joutwate/mtls-springboot
  
- https://codenotfound.com/spring-ws-mutual-authentication-example.html
- https://dzone.com/articles/hakky54mutual-tls-1
- https://livebook.manning.com/book/microservices-security-in-action/chapter-6/v-3/1
- https://www.dhaval-shah.com/rest-client-with-desired-nfrs-using-springs-resttemplate/

SSL
- https://www.opencodez.com/java/implement-2-way-authentication-using-ssl.htm

Spring
- https://medium.com/tech-grupozap/pensando-melhor-sobre-conex%C3%B5es-http-e7105ccf8b56
- https://www.thomasvitale.com/https-spring-boot-ssl-certificate/



