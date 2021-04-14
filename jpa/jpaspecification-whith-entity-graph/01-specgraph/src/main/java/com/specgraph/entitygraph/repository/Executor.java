package com.specgraph.entitygraph.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
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

import static org.springframework.data.jpa.repository.query.QueryUtils.toOrders;
import static org.springframework.util.Assert.notNull;

@Component
public class Executor<T, ID> {

    @PersistenceContext
    private EntityManager em;

    public <S extends T> TypedQuery<S> getQuery(@Nullable Specification<S> spec,
                                                Class<S> domainClass,
                                                Pageable pageable) {

        Sort sort = pageable.isPaged() ? pageable.getSort() : Sort.unsorted();
        return getQuery(spec, domainClass, sort);
    }

    public <S extends T> TypedQuery<S> getQuery(@Nullable Specification<S> spec, Class<S> domainClass, Sort sort) {
        notNull(spec, "Spedification must not be null!");
        notNull(spec, "Sort must not be null!");
        notNull(domainClass, "Domain class must not be null!");

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<S> query = builder.createQuery(domainClass);

        Root<S> root = applySpecificationToCriteria(spec, domainClass, query);
        query.select(root);

        if (sort.isSorted()) {
            query.orderBy(toOrders(sort, root, builder));
        }

        return em.createQuery(query);
    }

    protected <S, U extends T> Root<U> applySpecificationToCriteria(@Nullable Specification<U> spec,
                                                                    Class<U> domainClass,
                                                                    CriteriaQuery<S> query) {

        notNull(domainClass, "Domain class must not be null!");
        notNull(query, "CriteriaQuery must not be null!");

        Root<U> root = query.from(domainClass);

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

    public <S extends T> Page<S> readPage(TypedQuery<S> query,
                                             final Class<S> domainClass,
                                             Pageable pageable,
                                             @Nullable Specification<S> spec) {

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

    protected <S extends T> TypedQuery<Long> getCountQuery(@Nullable Specification<S> spec, Class<S> domainClass) {

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);

        Root<S> root = applySpecificationToCriteria(spec, domainClass, query);

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
