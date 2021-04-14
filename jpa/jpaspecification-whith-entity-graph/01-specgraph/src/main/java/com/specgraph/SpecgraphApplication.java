package com.specgraph;

import com.specgraph.entitygraph.repository.specentitygraph.CharacteristicsJpaSpecificationRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories(
//    basePackages = {"com.specgraph.entitygraph.repository.specentitygraph" }, 
//    repositoryBaseClass = CharacteristicsJpaSpecificationRepository.class
//)
public class SpecgraphApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpecgraphApplication.class, args);
    }

}
