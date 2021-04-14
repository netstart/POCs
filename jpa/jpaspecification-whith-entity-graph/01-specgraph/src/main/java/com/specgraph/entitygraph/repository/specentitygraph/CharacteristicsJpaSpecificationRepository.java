package com.specgraph.entitygraph.repository.specentitygraph;

import com.specgraph.entitygraph.model.Characteristic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Repository
public class CharacteristicsJpaSpecificationRepository extends SimpleJpaRepository<Characteristic, Long> {

    private final EntityManager em;

    public CharacteristicsJpaSpecificationRepository(Class<Characteristic> domainClass, EntityManager em) {
        super(domainClass, em);
        this.em = em;
    }

    public Page<Characteristic> findByTypeUsingSpecification(String type, Pageable pageable) {
        Specification<Characteristic> spec =
            (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("type"), type);

        return findAll(spec, pageable, EntityGraphType.FETCH, "Characteristic.item");
    }

    public Page<Characteristic> findAll(Specification<Characteristic> spec,
                                        Pageable pageable,
                                        EntityGraph.EntityGraphType entityGraphType,
                                        String entityGraphName) {

        TypedQuery<Characteristic> query = getQuery(spec, pageable.getSort());
        query.setHint(entityGraphType.getKey(), em.getEntityGraph(entityGraphName));
        return readPage(query, Characteristic.class, pageable, spec);
    }

}