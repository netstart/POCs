package com.specgraph.entitygraph.repository;

import com.specgraph.entitygraph.model.Characteristic;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.lang.Nullable;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.data.jpa.repository.query.QueryUtils.toOrders;
import static org.springframework.util.Assert.notNull;

@DataJpaTest
@Sql(scripts = "/entitygraph-data.sql")
public class CharacteristicsJpaSpecificationTest {

    @PersistenceContext // this will inject em in your class
    private EntityManager em;

    @Test
    public void find() {

        int pageNumber = 1;
        int pageSize = 10;
        PageRequest pageable = PageRequest.of(pageNumber, pageSize);

        Specification<Characteristic> spec =
            (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("type"), "Rigid");

        TypedQuery<Characteristic> query = getQuery(spec, Characteristic.class, pageable.getSort());
        query.setHint(EntityGraphType.FETCH.getKey(), em.getEntityGraph("Characteristic.item"));
        Page<Characteristic> page = readPage(query, Characteristic.class, pageable, spec);

        assertThat(page.getTotalElements()).isEqualTo(1);
    }

    public TypedQuery<Characteristic> getQuery(@Nullable Specification<Characteristic> spec,
                                               Class<Characteristic> domainClass,
                                               Sort sort) {
        notNull(spec, "Spedification must not be null!");
        notNull(spec, "Sort must not be null!");
        notNull(domainClass, "Domain class must not be null!");

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Characteristic> query = builder.createQuery(domainClass);

        Root<Characteristic> root = applySpecificationToCriteria(spec, domainClass, query);
        query.select(root);

        if (sort.isSorted()) {
            query.orderBy(toOrders(sort, root, builder));
        }

        return em.createQuery(query);
    }

    protected Root<Characteristic> applySpecificationToCriteria(@Nullable Specification<Characteristic> spec,
                                                                Class<Characteristic> domainClass,
                                                                CriteriaQuery<?> query) {

        notNull(domainClass, "Domain class must not be null!");
        notNull(query, "CriteriaQuery must not be null!");

        Root<Characteristic> root = query.from(domainClass);

        if (spec == null) {
            return root;
        }

        CriteriaBuilder builder = em.getCriteriaBuilder();
        Predicate predicate = spec.toPredicate(root, query, builder);

        if (predicate != null) {
            query.where(predicate);
        }

        return root;
    }

    public Page<Characteristic> readPage(TypedQuery<Characteristic> query,
                                         final Class<Characteristic> domainClass,
                                         Pageable pageable,
                                         @Nullable Specification<Characteristic> spec) {

        if (pageable.isPaged()) {
            query.setFirstResult((int) pageable.getOffset());
            query.setMaxResults(pageable.getPageSize());
        }

        return PageableExecutionUtils.getPage(query.getResultList(), pageable,
            () -> executeCountQuery(getCountQuery(spec, domainClass)));
    }

    private static long executeCountQuery(TypedQuery<Long> query) {
        Assert.notNull(query, "TypedQuery must not be null!");

        List<Long> totals = query.getResultList();
        long total = 0L;

        for (Long element : totals) {
            total += element == null ? 0 : element;
        }

        return total;
    }

    protected TypedQuery<Long> getCountQuery(@Nullable Specification<Characteristic> spec,
                                             Class<Characteristic> domainClass) {

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);

        Root<Characteristic> root = applySpecificationToCriteria(spec, domainClass, query);

        if (query.isDistinct()) {
            query.select(builder.countDistinct(root));
        } else {
            query.select(builder.count(root));
        }

        // Remove all Orders the Specifications might have applied
        query.orderBy(Collections.<Order>emptyList());

        return em.createQuery(query);
    }

}