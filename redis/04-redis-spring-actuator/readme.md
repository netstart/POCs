

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


#### Cria registros no redis usando RedisTemplate

curl -s -X POST http://localhost:8080/company/cache/1010 -H "Content-Type: application/json"

Executa o método info
curl -s http://localhost:8080/company/cache/ -H "Content-Type: application/json"


Ver as métricas em: http://localhost:8080/actuator/prometheus

Acompanhar: protection_scan_key_total_query_all

Criar a métrica abrindo:
- http://localhost:8080/meter/total-query
- http://localhost:8080/meter/total-invalid-query
- http://localhost:8080/meter/total-bloqueado

