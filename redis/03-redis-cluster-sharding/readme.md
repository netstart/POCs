https://medium.com/opstree-technology/redis-cluster-setup-sharding-and-failover-testing-cbf12d063d2c

O comando abaixo, irá executar o redis-server, passando um arquivo de configuração. O arquivo habilita o modo de cluster, e define um arquivo temporário para guardar configurações que são feitas após ele estar em execução.

Execute:
```
redis-server node7001.conf
redis-server node7002.conf
redis-server node7003.conf
```

O comando abaixo cria o cluster, fazendo com que ele esteja configurado no modo sharding, ou seja, os dados serão divididos entre os três nós.

```
redis-cli --cluster create 127.0.0.1:7001 127.0.0.1:7002 127.0.0.1:7003
```

Com a opção abaixo,`--cluster-replicas 1` irá adicionar um nó extra para réplica.

```
redis-cli --cluster create 127.0.0.1:7001 127.0.0.1:7002 127.0.0.1:7003 --cluster-replicas 1
```

Connecta em um dos nós

```
redis-cli -c -h localhost -p 7001
```

Uma vez conectado salva informações, e mostra onde está sendo salvo devido aos parâmetros passados no momento de conectar
```
set nome Clayton
set cidade Maringa
set pais Brasil
```

