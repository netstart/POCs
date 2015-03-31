package com.github.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.jdbc.Work;
import org.hibernate.metadata.ClassMetadata;

public interface Dao<T, PK> extends Serializable {

	public void remove(T instance);

	public T saveOrUpdate(T instance);

	public T load(PK primaryKey);

	public List<T> findAll();

	public List<T> findByHql(String query, Object[] params);

	public List<T> findByHql(String query);

	public List<T> findAll(Integer limit, Integer offset);

	public List<T> findAllUnlimited();

	public T findUniqueByHql(String query, Object[] params);

	public T loadFirst();

	public Long count();

	@SuppressWarnings("rawtypes")
	List findByCriteria(final DetachedCriteria detachedCriteria);

	List<T> findByCriteria(final DetachedCriteria criteria,
			final int firstResult, final int maxResults);

	void flush();

	@SuppressWarnings("rawtypes")
	List findAnyByHql(String query, Object[] params);

	@SuppressWarnings("rawtypes")
	List findAnyByHql(String query);

	Query createQuery(String queryString);

	void doWork(Work work);

	T findByCriteriaUnique(DetachedCriteria criteria);

	<R> R findByCriteriaUniqueAny(DetachedCriteria criteria);

	T fetch(T instance, String profile);

	void enableFetchProfile(String profile);

	void disableFetchProfile(String profile);

	void initializeProxy(Object obj);

	SQLQuery createSQLQuery(Class<T> clazz, String queryString);

	SQLQuery createSQLQuery(String queryString);

	T refresh(T instance);

	T findFirst();

	T merge(T instance);

	void persist(Object instance);

	Query getNamedQuery(String namedQuery);

	List findAnyByHqlOpenSession(String query, Object[] params);

	void evict(Object instance);

	List<T> find(String query);

	List<T> find(String query, Object[] params);

	List<T> listByHql(String queryString);

	Map<String, ClassMetadata> getAllClassMetadata();

	boolean isOpen();

	void clear();

	T load(Class<T> clazz, Serializable key);

	List<T> findByCriteriaAny(DetachedCriteria criteria, int firstResult,
			int maxResults);
}
