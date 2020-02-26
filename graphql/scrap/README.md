# Getting Started

`./mvnw clean package`

`./mvnw spring-boot:run`

Open: http://localhost:8080/graphiql

#### Query example:
```
query {
  findChangePlan(site: "smartmei.com.br") {
    description
    now
    brl
    usd
    eur
  }
}
```
![alt text](image\consulta01.PNG "Consulta com sucesso")

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/maven-plugin/)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/reference/htmlsingle/#boot-features-developing-web-applications)
* [GraphQL Java](https://www.graphql-java.com/)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
