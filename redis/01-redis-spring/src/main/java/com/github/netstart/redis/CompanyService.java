package com.github.netstart.redis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
/**
 * https://docs.spring.io/spring/docs/3.2.x/spring-framework-reference/html/cache.html
 */

public class CompanyService {

	private Map<Long, Company> companies = new HashMap<>();

	/**
	 * Ao consultar todos os objetos inclui o objeto Java Serializado no cache com a chave: "Company::findAll"
	 */

	@Cacheable(cacheNames = Company.CACHE_NAME, key = "#root.method.name")
	public List<Company> findAll() {
		System.out.println("findAll");
		return new ArrayList<>(companies.values());
	}

	/**
	 * Ao consultar, inclui o objeto Java Serializado no cache com a chave "Company::1010"
	 */
	@Cacheable(cacheNames = Company.CACHE_NAME, key = "#id")
	public Company findbyId(final Long id) {
		System.out.println("findbyId");
		return companies.get(id);
	}

	/**
	 * Se existir, exclui do cache, e NÃO atualiza com a nova informação
	 */
	@CacheEvict(cacheNames = Company.CACHE_NAME, allEntries = true)
	public Company create(final Company company) {
		System.out.println("create");
		companies.put(company.getId(), company);
		return company;
	}

	/**
	 * Atualiza o cache se existir, se não existir da erro
	 */
	@CachePut(cacheNames = Company.CACHE_NAME, key = "#company.getId()")
	public Company update(final Company company) {
		System.out.println("update");
		if (company.getId() == null) {
			throw new EntityNotFoundException("Identifier is empty");
		}
		companies.put(company.getId(), company);

		return company;
	}

	/**
	 * Se existir, exclui do cache
	 */
	@CacheEvict(cacheNames = Company.CACHE_NAME, key = "#id")
	public void delete(final Long id) {
		System.out.println("delete");
		if (id == null) {
			throw new EntityNotFoundException("Identifier is empty");
		}

		companies.remove(id);
	}

	/**
	 * Apesar de criar o cache, no método findCompositebyIdCache.
	 * 
	 * Não é utilizado pra recuperar o dado, não funciona como memoize
	 */
	public Company findCompositebyId(final Long id) {
		System.out.println("findCompositebyId");
		return findCompositebyIdCacheble(companies.get(id));
	}

	/** ha situações como esta que o cache não é criada
	 */
	@Cacheable(cacheNames = "findCompositebyIdCacheble",  key = "#company.id")
	public Company findCompositebyIdCacheble(Company company) {
		System.out.println("cache");
		// assim não serve como memoize
		return company;
		
		// assim nem cria o cache
		// return findByIdWithoutCache(company.getId());
	}
	
	
	/** Irá utilizar todos os atributos de entrada do método para formar a chave
	 * 
	 * A chave ficará assim:  "Company::SimpleKey [Company 10301030,1030]"
	 * 
	 * Poderia fazer assim 	@Cacheable(value =Company.CACHE_NAME, key="#name + #id" )
	 * "Company::Company 3 1030"
	 */
//	@Cacheable(cacheNames = Company.CACHE_NAME)
	@Cacheable(value =Company.CACHE_NAME, key="#name + #id" )
	public Company cacheUsingAllInputAttributes(String name, Long id) {
		System.out.println("usingAllInputAttributes");
		return companies.get(id);
	}
	
	
	public Company findByIdWithoutCache(Long id) {
		return companies.get(id);
	}
}
