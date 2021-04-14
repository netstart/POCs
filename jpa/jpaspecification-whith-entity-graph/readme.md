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

