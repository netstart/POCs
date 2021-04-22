package com.specgraph.entitygraph.repository.specentitygraph;

import com.specgraph.entitygraph.model.Post;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class MyRepositoryConfiguration {

    @PersistenceContext // this will inject em in your class
    private EntityManager em;

    @Bean
    public PostJpaSpecificationRepository getPostJpaSpecificationRepository() {
        return new PostJpaSpecificationRepository(Post.class, em);
    }

}
