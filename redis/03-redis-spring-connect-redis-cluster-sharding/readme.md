

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
minikube config set memory 6144
minikube config set cpus 3

#### Docker image 

```
docker login
mvn clean package
docker build --build-arg JAR_FILE=target/*.jar -t netstart/spring-redis:latest .
docker push netstart/spring-redis:latest
kubectl run k8s-deployment-spring-redis --image=netstart/spring-redis:latest
```

* The flag –image-pull-policy Never ensures, that Minikube doesn't try to pull the image from a registry, but takes it from the local Docker host instead.


kubectl get pods

```
NAME                                            READY   STATUS             RESTARTS   AGE
k8s-deployment-spring-redis                    0/1     ImagePullBackOff   0          87s
```

kubectl exec -ti k8s-deployment-spring-redis  bash

kubectl exec k8s-deployment-spring-redis env

kubectl expose deployment/k8s-deployment-spring-redis --type="NodePort" --port 8080

kubectl describe services/k8s-deployment-spring-redis

minikube ip

https://gorillalogic.com/blog/build-and-deploy-a-spring-boot-app-on-kubernetes-minikube/
https://www.baeldung.com/spring-boot-minikube

####

To get your password run:

    export REDIS_PASSWORD=$(kubectl get secret --namespace default my-release-redis-cluster -o jsonpath="{.data.redis-password}" | base64 --decode)

You have deployed a Redis Cluster accessible only from within you Kubernetes Cluster.INFO: The Job to create the cluster will be created.To connect to your Redis cluster:

1. Run a Redis pod that you can use as a client:
kubectl run --namespace default my-release-redis-cluster-client --rm --tty -i --restart='Never' \
 --env REDIS_PASSWORD=$REDIS_PASSWORD \
--image docker.io/bitnami/redis-cluster:6.0.5-debian-10-r27 -- bash

2. Connect using the Redis CLI:

redis-cli -c -h my-release-redis-cluster -a $REDIS_PASSWORD



#### Diferença ao usar keys * e keys do redisTemplate

Executar `keys *` através do cliente `redis-cli` tem resultado diferente de usar o `redisTemplate.keys("*")`, o spring vai em cada nó e realiza a consulta e consolida para nós, `redis-cli` não. O `redis-cli` consulta apenas no nó corrente. 


#### [About Spring Redis Cluster support](https://docs.spring.io/spring-data/data-redis/docs/current/reference/html/#cluster)

While redirects for specific keys to the corresponding slot-serving node are handled by the driver libraries, higher-level functions, such as collecting information across nodes or sending commands to all nodes in the cluster, are covered by RedisClusterConnection. Picking up the keys example from earlier, this means that the keys(pattern) method picks up every master node in the cluster and simultaneously executes the KEYS command on every master node while picking up the results and returning the cumulated set of keys. To just request the keys of a single node RedisClusterConnection provides overloads for those methods (for example, keys(node, pattern))


RedisTemplate provides access to cluster-specific operations through the [ClusterOperations interface](https://docs.spring.io/spring-data/data-redis/docs/current/reference/html/#cluster.redistemplate), which can be obtained from RedisTemplate.opsForCluster(). This lets you explicitly run commands on a single node within the cluster while retaining the serialization and deserialization features configured for the template. It also provides administrative commands (such as CLUSTER MEET) or more high-level operations (for example, resharding)

KEYS * runs over all the keys in your DB and counts their total. Again, Redis a single-threaded database. This means that during this expensive process, the DB is unresponsive to your application! NEVER run this command in a production environment.

SMEMBERS is a command that returns the values stored per key in a given set. If the purpose of running the command is simply to count the number of active sessions, do NOT use this command. Its cardinality is O(n) (similar to KEYS command above), and could “stop the world” in the middle of your production. Based on your Spring Session setup, you might have a set with the key FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME that can hold all / many of the sessions in the system, which makes this command not very different than KEYS.


