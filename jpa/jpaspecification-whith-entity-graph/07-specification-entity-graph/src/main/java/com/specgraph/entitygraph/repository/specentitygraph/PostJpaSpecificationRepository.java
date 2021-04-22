package com.specgraph.entitygraph.repository.specentitygraph;

import com.specgraph.entitygraph.model.Post;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

import javax.persistence.EntityManager;

@NoRepositoryBean
public class PostJpaSpecificationRepository extends JpaSpecificationEntityGraphRepositoryImpl<Post, Long> {

  public PostJpaSpecificationRepository(Class<Post> domainClass, EntityManager em) {
    super(domainClass, em);
  }

  public List<Post> findSubjectSpecification(String subject, String entityGraphName) {
    Specification<Post> spec = (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("subject"), subject);

    return findAll(spec, EntityGraphType.FETCH, entityGraphName);
  }

}