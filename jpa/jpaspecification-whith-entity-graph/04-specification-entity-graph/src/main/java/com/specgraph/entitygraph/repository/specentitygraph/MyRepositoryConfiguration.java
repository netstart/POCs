package com.specgraph.entitygraph.repository.specentitygraph;

import com.specgraph.entitygraph.model.Characteristic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class MyRepositoryConfiguration {

    @PersistenceContext // this will inject em in your class
    private EntityManager em;

    @Bean
    public CharacteristicsJpaSpecificationRepository getCharacteristicsJpaSpecificationRepository() {
        return new CharacteristicsJpaSpecificationRepository(Characteristic.class, em);
    }

}
