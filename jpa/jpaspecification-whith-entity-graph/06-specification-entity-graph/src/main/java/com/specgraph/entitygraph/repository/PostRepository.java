package com.specgraph.entitygraph.repository;

import com.specgraph.entitygraph.model.Post;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Component
public class PostRepository {

  @PersistenceContext
  private EntityManager entityManager;

  public Post find(Long id) {
    Post post = entityManager.find(Post.class, id);

// necessário fechar se o entitymanter for criado na mão, ao invés de injetar
// entityManager.close();
    return post;
  }

  public Post findWithEntityGraph(Long id) {
    EntityGraph entityGraph = entityManager.getEntityGraph("post-entity-graph");
    Map<String, Object> properties = new HashMap<>();
    properties.put("javax.persistence.fetchgraph", entityGraph);
    Post post = entityManager.find(Post.class, id, properties);
    
// necessário fechar se o entitymanter for criado na mão, ao invés de injetar
// entityManager.close();
    return post;
  }

  public Post findWithEntityGraph2(Long id) {

    EntityGraph<Post> entityGraph = entityManager.createEntityGraph(Post.class);
    entityGraph.addAttributeNodes("subject");
    entityGraph.addAttributeNodes("user");
    entityGraph.addSubgraph("comments").addAttributeNodes("user");

    Map<String, Object> properties = new HashMap<>();
    properties.put("javax.persistence.fetchgraph", entityGraph);
    Post post = entityManager.find(Post.class, id, properties);

// necessário fechar se o entitymanter for criado na mão, ao invés de injetar
// entityManager.close();
    return post;
  }

  public Post findUsingJpql(Long id) {
    EntityGraph entityGraph = entityManager.getEntityGraph("post-entity-graph-with-comment-users");
    Post post = entityManager.createQuery("Select p from Post p where p.id=:id", Post.class).setParameter("id", id)
      .setHint("javax.persistence.fetchgraph", entityGraph).getSingleResult();

    entityManager.close();
    return post;
  }

  public Post findUsingCriteria(Long id) {

    EntityGraph entityGraph = entityManager.getEntityGraph("post-entity-graph-with-comment-users");
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Post> criteriaQuery = criteriaBuilder.createQuery(Post.class);
    Root<Post> root = criteriaQuery.from(Post.class);
    criteriaQuery.where(criteriaBuilder.equal(root.<Long>get("id"), id));
    TypedQuery<Post> typedQuery = entityManager.createQuery(criteriaQuery);
    typedQuery.setHint("javax.persistence.loadgraph", entityGraph);
    Post post = typedQuery.getSingleResult();

// necessário fechar se o entitymanter for criado na mão, ao invés de injetar 
// entityManager.close();
    return post;
  }

}