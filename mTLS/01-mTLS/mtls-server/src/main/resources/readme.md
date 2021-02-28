- `server.ssl.key-store=classpath:server-nonprod.jks` - The path to the key store that contains the SSL certificate. In our example, we want Spring Boot to look for it in the classpath.
- `server.ssl.key-store-password=changeme` - The password used to access the key store.
- `server.ssl.key-store-type:` - The type of the key store (JKS or PKCS12).
- `server.ssl.key-alias:` - The alias that identifies the key in the key store.
- `server.ssl.key-password:` - The password used to access the key in the key store.
- `server.ssl.client-auth=need` - The 'client-auth' property specifies whether client authentication is wanted (“want”) or needed (“need”).
In this example we set it to 'need' as we want to assure two-way SSL is established.
The server’s truststore and the corresponding password are also configured so that the public certificate of the client is trusted.




### Spring properties:
- https://docs.spring.io/spring-boot/docs/current/reference/html/appendix-application-properties.html#server.ssl.ciphers