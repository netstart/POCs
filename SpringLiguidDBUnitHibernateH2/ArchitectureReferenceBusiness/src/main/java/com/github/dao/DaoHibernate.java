package com.github.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.Work;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.transaction.annotation.Transactional;

import com.github.entity.ALogger;

public class DaoHibernate<T, PK> extends ALogger implements Dao<T, PK> {

	private static final long serialVersionUID = -795755904290863602L;
	private static final int MAXRESULTS = 50;

	@SuppressWarnings("rawtypes")
	private Class typeClass;

	@Autowired
	private SessionFactory sessionFactory;

	public DaoHibernate(@SuppressWarnings("rawtypes") Class argClass) {
		typeClass = argClass;
	}

	public Boolean containSessionFactory() {
		return sessionFactory != null;
	}

	protected RuntimeException catchException(Throwable re) {
		RuntimeException databaseException = new RuntimeException(re);
		return databaseException;
	}

	public T saveOrUpdate(T instance) {
		LOG.debug("saving " + instance.getClass().getName() + " instance");
		try {
			instance = merge(instance);
			LOG.debug("save successful");
			return instance;
		} catch (RuntimeException re) {
			LOG.error("save failed", re);
			throw catchException(re);
		}
	}

	@Transactional
	public List<T> findAll() {
		return findAll(MAXRESULTS, 0);
	}

	private DetachedCriteria createCriteria() {
		DetachedCriteria criteria = DetachedCriteria.forClass(typeClass);
		criteria.setLockMode(LockMode.READ);
		criteria.addOrder(Order.asc("id"));

		return criteria;
	}

	public void remove(T instance) {
		LOG.debug("deleting " + instance.getClass().getName() + " instance");
		try {
			sessionFactory.getCurrentSession().delete(instance);
			LOG.debug("delete successful");
		} catch (RuntimeException re) {
			LOG.error("delete failed", re);
			throw catchException(re);
		}
	}

	@SuppressWarnings("unchecked")
	public T load(PK primaryKey) {
		LOG.debug("Load instance");
		try {
			return (T) load(typeClass, (Serializable) primaryKey);
		} catch (RuntimeException re) {
			LOG.error("load failed", re);
			throw catchException(re);
		}
	}

	public List<T> findByHql(String query, Object[] params) {
		LOG.debug("Find By Hsql " + query);
		try {

			if (params != null && params.length > 0) {
				return find(query, params);
			} else {
				return find(query);
			}
		} catch (RuntimeException re) {
			LOG.error("load failed", re);
			throw catchException(re);
		}
	}

	public List<T> findByHql(String query) {
		return findByHql(query, null);
	}

	public List<T> findAll(Integer limit, Integer offset) {
		LOG.debug("finding all " + typeClass.getName() + " instances from: "
				+ offset + " to: " + limit);
		try {
			List<T> result = findByCriteria(createCriteria(), offset, limit);

			LOG.debug("findAll successful, result size: " + result.size());
			return result;
		} catch (RuntimeException re) {
			LOG.error("findAll failed", re);
			throw catchException(re);
		}
	}

	public List<T> findAllUnlimited() {
		LOG.debug("finding all " + typeClass.getName() + " instances");
		try {
			List<T> result = findByCriteria(createCriteria());

			LOG.debug("findAll successful, result size: " + result.size());
			return result;
		} catch (RuntimeException re) {
			LOG.error("findAll failed", re);
			throw catchException(re);
		}
	}

	public Long count() {
		List<Long> count = findAnyByHql("select count(*) from "
				+ typeClass.getName());

		return count.get(0);
	}

	public T loadFirst() {
		List<T> result = findAll(1, 0);
		if (result.isEmpty()) {
			return null;
		}

		return result.get(0);
	}

	public T findUniqueByHql(String query, Object[] params) {
		List<T> result = findByHql(query, params);
		return DataAccessUtils.uniqueResult(result);
	}

	public T refresh(T instance) {
		sessionFactory.getCurrentSession().refresh(instance);
		return instance;
	}

	public T findFirst() {
		List<T> list = findAll(1, 1);
		if (list.size() == 0) {
			return null;
		} else {
			return list.get(0);
		}
	}

	public List<T> findByCriteria(DetachedCriteria detachedCriteria) {
		return detachedCriteria.getExecutableCriteria(
				sessionFactory.getCurrentSession()).list();
	}

	public List<T> findByCriteria(DetachedCriteria criteria, int firstResult,
			int maxResults) {

		Session currentSession = sessionFactory.getCurrentSession();

		Criteria executableCriteria = criteria
				.getExecutableCriteria(currentSession);

		List list = executableCriteria.setFirstResult(firstResult)
				.setMaxResults(maxResults).list();

		return list;

	}

	public List<T> findByCriteriaAny(DetachedCriteria criteria,
			int firstResult, int maxResults) {
		return findByCriteria(criteria, firstResult, maxResults);
	}

	@SuppressWarnings("unchecked")
	public T findByCriteriaUniqueAny(DetachedCriteria criteria) {
		return findByCriteriaUnique(criteria);
	}

	public T load(Class<T> clazz, Serializable key) {
		return (T) sessionFactory.getCurrentSession().get(clazz, key);
	}

	public Query createQuery(String queryString) {
		return sessionFactory.getCurrentSession().createQuery(queryString);
	}

	public SQLQuery createSQLQuery(String queryString) {
		return sessionFactory.getCurrentSession().createSQLQuery(queryString);
	}

	public SQLQuery createSQLQuery(Class<T> clazz, String queryString) {
		return sessionFactory.getCurrentSession().createSQLQuery(queryString)
				.addEntity(clazz);
	}

	public T merge(T instance) {
		return (T) sessionFactory.getCurrentSession().merge(instance);
	}

	public void persist(Object instance) {
		sessionFactory.getCurrentSession().persist(instance);
	}

	public void flush() {
		sessionFactory.getCurrentSession().flush();
	}

	public void clear() {
		sessionFactory.getCurrentSession().clear();
	}

	public boolean isOpen() {
		return sessionFactory.getCurrentSession().isOpen();
	}

	public Map<String, ClassMetadata> getAllClassMetadata() {
		return sessionFactory.getAllClassMetadata();
	}

	public List<T> listByHql(String queryString) {
		return sessionFactory.getCurrentSession().createQuery(queryString)
				.list();
	}

	@SuppressWarnings("unchecked")
	public List<T> find(String query, Object[] params) {

		return findAnyByHql(query, params);
	}

	@SuppressWarnings("unchecked")
	public List<T> find(String query) {
		return findAnyByHql(query);
	}

	@SuppressWarnings("unchecked")
	public T findByCriteriaUnique(DetachedCriteria criteria) {
		return ((T) criteria.getExecutableCriteria(
				sessionFactory.getCurrentSession()).uniqueResult());
	}

	public void doWork(Work work) {

		sessionFactory.getCurrentSession().doWork(work);
	}

	@SuppressWarnings({ "rawtypes" })
	public List findAnyByHql(String query, Object[] params) {

		Session session = sessionFactory.getCurrentSession();

		return internalFindAnyByHql(query, params, session);
	}

	@SuppressWarnings("rawtypes")
	private List internalFindAnyByHql(String query, Object[] params,
			Session session) {
		Query queryObject = session.createQuery(query);

		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				queryObject.setParameter(i, params[i]);
			}
		}

		return queryObject.list();
	}

	@SuppressWarnings("rawtypes")
	public List findAnyByHql(String query) {
		return findAnyByHql(query, null);
	}

	public void evict(Object instance) {
		sessionFactory.getCurrentSession().evict(instance);
	}

	public T fetch(T instance, String profile) {
		Session session = sessionFactory.getCurrentSession();
		Serializable id = session.getIdentifier(instance);
		enableFetchProfile(profile);
		try {
			return findByCriteriaUnique(DetachedCriteria.forClass(
					instance.getClass()).add(Restrictions.idEq(id)));
		} finally {
			disableFetchProfile(profile);
		}
	}

	public void disableFetchProfile(String profile) {
		sessionFactory.getCurrentSession().enableFetchProfile(profile);
	}

	public void enableFetchProfile(String profile) {
		sessionFactory.getCurrentSession().disableFetchProfile(profile);

	}

	public void initializeProxy(Object obj) {
		Hibernate.initialize(obj);
	}

	@SuppressWarnings("rawtypes")
	@Deprecated
	public List findAnyByHqlOpenSession(String query, Object[] params) {

		Session session = SessionFactoryUtils.getSession(sessionFactory, true);
		try {

			return internalFindAnyByHql(query, params, session);

		} finally {

			SessionFactoryUtils.closeSession(session);

		}

	}

	// public SpExecutor getSpExecutor(String sp) {
	// return new SpExecutorImpl(this, sp);
	// }

	public Query getNamedQuery(String namedQuery) {
		return sessionFactory.getCurrentSession().getNamedQuery(namedQuery);
	}

}
