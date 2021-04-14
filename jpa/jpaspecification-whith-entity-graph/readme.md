### 01-specification-entity-graph

Repositories com consultas que funcionam utilizando a interface JPARepository e anotando o método com @EntityGraph. O Contra ponto é que não consigo escolher o grafo de objeto dinâmicamente, se eu precisar de algo dinâmico teria de criar uma consulta diferente pra cada opção, e anotar com @EntityGraph

```
CharacteristicsRepository, ItemRepository
```

Os comentários, são para testar e verificar a diferença no SQL gerado, ver classes de teste
`CharacteristicTest` e `ItemTest`

### 02-specification-entity-graph

Teste `CharacteristicsJpaSpecificationTest` utilizando os métodos retirados de `org.springframework.data.jpa.repository.support.SimpleJpaRepository` para avaliar se é possível utilizar Specification com Entity Graph de uma maneira mais dinâmica.


### 03-specification-entity-graph

Usando SimpleJpaRepository pra implementar a consulta, veja as classes `CharacteristicsJpaSpecificationRepository`, `MyRepositoryConfiguration` e `CharacteristicsJpaSpecificationRepositoryTest`

### 04-specification-entity-graph

Implementando abstração pra conter mais métodos/possibilidades de utilizar specification com entity graph, veja
`CharacteristicsJpaSpecificationRepository`, `JpaSpecificationEntityGraphRepositoryImpl` e `CharacteristicsJpaSpecificationRepositoryTest`


### 05-specification-entity-graph

Utilizando JpaRepository pra utilizar JPA Specification com Entity Query, veja:
`CharacteristicsRepository.findAll(Specification<Characteristic> specification, Pageable pageable)`
`CharacteristicsJpaSpecificationTest`



### References:

- https://www.baeldung.com/spring-data-jpa-named-entity-graphs
- https://github.com/eugenp/tutorials/tree/master/persistence-modules/spring-data-jpa-query
- https://www.baeldung.com/jpa-entity-graph
- https://github1s.com/eugenp/tutorials/blob/HEAD/persistence-modules/java-jpa/src/main/java/com/baeldung/jpa/entitygraph/MainApp.java
- https://stackoverflow.com/questions/26291143/spring-data-jpa-jpaspecificationexecutor-entitygraph/67099691#67099691



The default value of the type argument of the @EntityGraph annotation is EntityGraphType.FETCH. When we use this, the Spring Data module will apply the FetchType.EAGER strategy on the specified attribute nodes. And for others, the FetchType.LAZY strategy will be applied.

So in our case, the characteristics property will be loaded eagerly, even though the default fetch strategy of the @OneToMany annotation is lazy.

One catch here is that if the defined fetch strategy is EAGER, then we cannot change its behavior to LAZY. This is by design since the subsequent operations may need the eagerly fetched data at a later point during the execution.






