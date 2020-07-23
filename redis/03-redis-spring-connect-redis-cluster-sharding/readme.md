

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

#### Minikube
minikube config set memory 6096
minikube config set cpus 3

#### Docker image 

```
docker login
docker build --build-arg JAR_FILE=target/*.jar -t netstart/spring-redis:latest .
docker push netstart/spring-redis:latest
kubectl run k8s-deployment-spring-redis --image=netstart/spring-redis:latest
```

kubectl run {DEPLOYMENT_NAME} --image= {YOUR_IMAGE} --port=8080

* The flag –image-pull-policy Never ensures, that Minikube doesn't try to pull the image from a registry, but takes it from the local Docker host instead.

kubectl get deployments


kubectl get pods

```
NAME                                            READY   STATUS             RESTARTS   AGE
spring-redis-65d94bb588-ftpkz                   0/1     ImagePullBackOff   0          87s
```

kubectl exec -ti spring-redis-65d94bb588-ftpkz bash

kubectl exec spring-redis-65d94bb588-ftpkz env

kubectl expose deployment/k8s-deployment-spring-redis --type="NodePort" --port 8080

kubectl describe services/k8s-deployment-spring-redis

minikube ip

https://gorillalogic.com/blog/build-and-deploy-a-spring-boot-app-on-kubernetes-minikube/
https://www.baeldung.com/spring-boot-minikube

#### Diferença ao usar keys * e keys do redisTemplate

Executar `keys *` através do cliente `redis-cli` tem resultado diferente de usar o `redisTemplate.keys("*")`, o spring vai em cada nó e realiza a consulta e consolida para nós, `redis-cli` não. O `redis-cli` consulta apenas no nó corrente. 


#### [About Spring Redis Cluster support](https://docs.spring.io/spring-data/data-redis/docs/current/reference/html/#cluster)

While redirects for specific keys to the corresponding slot-serving node are handled by the driver libraries, higher-level functions, such as collecting information across nodes or sending commands to all nodes in the cluster, are covered by RedisClusterConnection. Picking up the keys example from earlier, this means that the keys(pattern) method picks up every master node in the cluster and simultaneously executes the KEYS command on every master node while picking up the results and returning the cumulated set of keys. To just request the keys of a single node RedisClusterConnection provides overloads for those methods (for example, keys(node, pattern))


RedisTemplate provides access to cluster-specific operations through the [ClusterOperations interface](https://docs.spring.io/spring-data/data-redis/docs/current/reference/html/#cluster.redistemplate), which can be obtained from RedisTemplate.opsForCluster(). This lets you explicitly run commands on a single node within the cluster while retaining the serialization and deserialization features configured for the template. It also provides administrative commands (such as CLUSTER MEET) or more high-level operations (for example, resharding)

