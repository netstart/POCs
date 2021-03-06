package com.specgraph.entitygraph.repository.specentitygraph;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@NoRepositoryBean
public class JpaSpecificationEntityGraphRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID>
  implements JpaSpecificationEntityGraphRepository<T, ID> {

  private EntityManager em;
  private Class<T> domainClass;

  public JpaSpecificationEntityGraphRepositoryImpl(Class<T> domainClass, EntityManager em) {
    super(domainClass, em);
    this.domainClass = domainClass;
    this.em = em;
  }

  @Override
  public List<T> findAll(Specification<T> spec, EntityGraph.EntityGraphType entityGraphType, String entityGraphName) {
//    TypedQuery<T> query = getQuery(spec, (Sort) null);
    
    TypedQuery<T> query = getQuery(spec, Sort.unsorted());
    query.setHint(entityGraphType.getKey(), em.getEntityGraph(entityGraphName));
    return query.getResultList();
  }

  @Override
  public Page<T> findAll(Specification<T> spec,
                         Pageable pageable,
                         EntityGraph.EntityGraphType entityGraphType,
                         String entityGraphName) {
    TypedQuery<T> query = getQuery(spec, pageable.getSort());
    query.setHint(entityGraphType.getKey(), em.getEntityGraph(entityGraphName));
    return readPage(query, domainClass, pageable, spec);
  }

  @Override
  public List<T> findAll(Specification<T> spec,
                         Sort sort,
                         EntityGraph.EntityGraphType entityGraphType,
                         String entityGraphName) {
    TypedQuery<T> query = getQuery(spec, sort);
    query.setHint(entityGraphType.getKey(), em.getEntityGraph(entityGraphName));
    return query.getResultList();
  }

  @Override
  public T findOne(Specification<T> spec, EntityGraph.EntityGraphType entityGraphType, String entityGraphName) {
//    TypedQuery<T> query = getQuery(spec, (Sort) null);
    TypedQuery<T> query = getQuery(spec, Sort.unsorted());
    query.setHint(entityGraphType.getKey(), em.getEntityGraph(entityGraphName));
    return query.getSingleResult();
  }

}