package br.com.elotech.daoLayer;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.jdbc.Work;

public interface Dao<T extends EntityDef, PK> {

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

	List<T> findByCriteria(final DetachedCriteria criteria, final int firstResult,
			final int maxResults);

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
}
