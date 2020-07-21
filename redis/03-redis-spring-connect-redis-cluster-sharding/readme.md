

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


=== Cria registros no redis usando RedisTemplate

curl -s -X POST http://localhost:8080/company/cache/1010 -H "Content-Type: application/json"

Executa o método info
curl -s http://localhost:8080/company/cache/ -H "Content-Type: application/json"


Executar `keys *` através do cliente `redis-cli` tem resultado diferente de usar o `redisTemplate.keys("*")`, o spring vai em cada nó e realiza a consulta e consolida para nós, `redis-cli` não. O `redis-cli` consulta apenas no nó corrente. 