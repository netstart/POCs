Original: https://github.com/bitnami/charts/tree/master/bitnami/redis


#### Minikube
minikube config set memory 6144
minikube config set cpus 3

Instala o Redis com Sentinel


```
helm install my-release bitnami/redis --values ./etc/redis/values.yaml

helm install my-release bitnami/redis --namespace YOURNAMESPACE --values ./etc/redis/values.yaml

```

Irá criar 3 services:

- The chart creates a headless service for internal access. To access the master node, it is necessary to get the name of the headless service which is running in all cluster pods. Run the following command: `kubectl get svc`

- Access the master node by running the redis-cli command as shown below. Remember to replace POD-NAME with the name of the pod you want to access and HEADLESS-SVC-NAME with the name of the headless service you get in the previous step:

`redis-cli -h POD-NAME.HEADLESS-SVC-NAME -a $REDIS_PASSWORD`


Cria pod client, para se conectar diretamente no redis-server ou no sentinel

```
kubectl run --namespace default my-release-redis-client --rm --tty -i --restart='Never' --image docker.io/bitnami/redis:6.0.5-debian-10-r32 -- bash
```


Conecta no Sentinel pelo service do K8s para executar comandos referente ao sentinel

```
redis-cli -h my-release-redis -p 26379 # Sentinel access
```

Comandos do sentinel

```
sentinel masters
sentinel failover mymaster
```

Conecta no Redis pelo service do K8s para executar comandos no próprio redis, em modo apenas leitura

```
redis-cli -h my-release-redis -p 6379 # Read only operations
```


Conecta da máquina host, no redis-master para poder executar comandos de escrita e leitura no redis, usando o redis-cli


Cria um redirecionamento de porta local para dentro do service do Kubernetes para poder coletar metricas da máquina local

```
kubectl port-forward svc/SVC-NAME PORT:PORT
kubectl port-forward svc/my-release-redis-metrics 9121:9121
curl localhost:9121/metrics
```

Enquanto o rediorecionamento de porta acima estiver em execução, o terminal ficará preso. Poderia executar o comando segundo plano, ou então utilizar outro terminal para coletar manualmente a métrica a fim de testar com o comando abaixo:

```
curl 127.0.0.1:9121/metrics
```


Para verificar se o fail over está funcionando, podemos dizer ao K8s para manter 0 (zero) réplicas do pod Master, e então após alguns segundos perguntar qual é o IP (quem é) do Master ao Sentinel. Abaixo você vê a sintaxe e o comando de exemplo para definir 0 replicas ao pod my-release-redis-master.

```
kubectl scale sts POD-ID --replicas=0
kubectl scale sts my-release-redis-master --replicas=0
```


```

To get your password run:
	export REDIS_PASSWORD=$(kubectl get secret --namespace default my-release-redis -o jsonpath="{.data.redis-password}" | base64 --decode)

To connect to your Redis server:
1. Run a Redis pod that you can use as a client:
   kubectl run --namespace default my-release-redis-client --rm --tty -i --restart='Never' \
    --env REDIS_PASSWORD=$REDIS_PASSWORD \
   --image docker.io/bitnami/redis:6.0.6-debian-10-r10 -- bash

2. Connect using the Redis CLI:
   redis-cli -h my-release-redis -p 6379 -a $REDIS_PASSWORD # Read only operations
   redis-cli -h my-release-redis -p 26379 -a $REDIS_PASSWORD # Sentinel access

```

Confira a criação do redis com os comandos:

```
$ kubectl get pods
$ kubectl get svc
$ kubectl get secret
```

Confira se o service que dá acesso às métricas está acessível, executando os comandos abaixo e verificando as métricas:

```
$ kubectl port-forward svc/my-release-redis-metrics 9121:9121
$ curl localhost:9121/metrics
```

Confira a instalação, acessando o service do Redis para criar e consultar dados:

```
$ kubectl port-forward service/my-release-redis 6379:6379
$ redis-cli -p 6379 -a PasswordToAccessRedis
$ setex key:teste:01 60 valueTest
$ keys *
```

Confira se o sentinel está em execução:

```
$ kubectl port-forward service/my-release-redis 26379:26379
$ redis-cli -p 26379
$ sentinel masters
```

Criar uma porta local fazendo redirecionamento para outro dentro do Cluster: `https://kubernetes.io/docs/tasks/access-application-cluster/port-forward-access-application-cluster/`


#### Cria registros no redis usando RedisTemplate

curl -s -X POST http://localhost:8080/company/cache/1010 -H "Content-Type: application/json"

Executa o método info
curl -s http://localhost:8080/company/cache/ -H "Content-Type: application/json"

#### Docker image 

Criar imagem docker deste projeto Java com Spring, enviar para o dockerhub, e então executar no cluster K8s


```
docker login
mvn clean package
docker build --build-arg JAR_FILE=target/*.jar -t netstart/spring-redis:latest .
docker push netstart/spring-redis:latest
kubectl run app-spring-redis --image=netstart/spring-redis:latest
```


kubectl get pods

```
NAME                                            READY   STATUS             RESTARTS   AGE
k8s-deployment-spring-redis                    0/1     ImagePullBackOff   0          87s
```

```
minikube ip
``` 


- https://gorillalogic.com/blog/build-and-deploy-a-spring-boot-app-on-kubernetes-minikube/
- https://www.baeldung.com/spring-boot-minikube

- https://github.com/bitnami/charts/tree/master/bitnami/redis
- https://redis.io/topics/sentinel
- https://docs.bitnami.com/general/infrastructure/kubernetes-sandbox/get-started/get-started/
- https://docs.bitnami.com/tutorials/deploy-redis-sentinel-production-cluster/

- https://docs.bitnami.com/tutorials/secure-kubernetes-services-with-ingress-tls-letsencrypt
- https://docs.bitnami.com/kubernetes/how-to/get-started-serverless-computing-kubeless/

- https://medium.com/trendyol-tech/high-availability-with-redis-sentinel-and-spring-lettuce-client-9da40525fc82

- headless service - https://kubernetes.io/docs/concepts/services-networking/service/#headless-services


#### Diferença ao usar keys * e keys do redisTemplate

Executar `keys *` através do cliente `redis-cli` tem resultado diferente de usar o `redisTemplate.keys("*")`, o spring vai em cada nó e realiza a consulta e consolida para nós, `redis-cli` não. O `redis-cli` consulta apenas no nó corrente. 


#### [About Spring Redis Cluster support](https://docs.spring.io/spring-data/data-redis/docs/current/reference/html/#cluster)

While redirects for specific keys to the corresponding slot-serving node are handled by the driver libraries, higher-level functions, such as collecting information across nodes or sending commands to all nodes in the cluster, are covered by RedisClusterConnection. Picking up the keys example from earlier, this means that the keys(pattern) method picks up every master node in the cluster and simultaneously executes the KEYS command on every master node while picking up the results and returning the cumulated set of keys. To just request the keys of a single node RedisClusterConnection provides overloads for those methods (for example, keys(node, pattern))


RedisTemplate provides access to cluster-specific operations through the [ClusterOperations interface](https://docs.spring.io/spring-data/data-redis/docs/current/reference/html/#cluster.redistemplate), which can be obtained from RedisTemplate.opsForCluster(). This lets you explicitly run commands on a single node within the cluster while retaining the serialization and deserialization features configured for the template. It also provides administrative commands (such as CLUSTER MEET) or more high-level operations (for example, resharding)

KEYS * runs over all the keys in your DB and counts their total. Again, Redis a single-threaded database. This means that during this expensive process, the DB is unresponsive to your application! NEVER run this command in a production environment.

SMEMBERS is a command that returns the values stored per key in a given set. If the purpose of running the command is simply to count the number of active sessions, do NOT use this command. Its cardinality is O(n) (similar to KEYS command above), and could “stop the world” in the middle of your production. Based on your Spring Session setup, you might have a set with the key FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME that can hold all / many of the sessions in the system, which makes this command not very different than KEYS.


