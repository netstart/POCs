package com.specgraph.entitygraph.repository.specentitygraph;

import com.specgraph.entitygraph.model.Characteristic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;

@NoRepositoryBean
public class CharacteristicsJpaSpecificationRepository extends JpaSpecificationEntityGraphRepositoryImpl<Characteristic, Long> {

    public CharacteristicsJpaSpecificationRepository(Class<Characteristic> domainClass, EntityManager em) {
        super(domainClass, em);
    }

    public Page<Characteristic> findByTypeUsingSpecification(String type, Pageable pageable) {
        Specification<Characteristic> spec =
            (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("type"), type);

        return findAll(spec, pageable, EntityGraphType.FETCH, "Characteristic.item");
    }

}