package com.github;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.dao.DaoDepartment;
import com.github.entity.Department;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/testCtxInitial.xml" })
//
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		DbUnitTestExecutionListener.class })
//
@DatabaseSetup(value = { "/dbunit/facade/department.dbunit.xml" })
@DatabaseTearDown(value = { "/dbunit/facade/department.dbunit.xml" }, type = DatabaseOperation.DELETE_ALL)
public class TestDepartment {

	@Autowired
	private DaoDepartment daoDepartment;


	@Test
	public void findDepartment() throws Exception {
		if (daoDepartment == null) {
			throw new Exception("daoDeparment is null, it´s crazy man!");
		}

		if (!daoDepartment.containSessionFactory()) {
			throw new Exception("sessionFactory is null, it´s crazy man!");
		}

		List<Department> findAll = daoDepartment.findAll();
		System.out.println(findAll.size());

		// CidadeEntity cidadeEntity = repositoryCidade.findByUFCidade("PR",
		// "CURITIBA");
		// assertNotNull(cidadeEntity);
		// assertEquals("CURITIBA", cidadeEntity.getDescricaoAbreviada());
		// assertEquals("PR", cidadeEntity.getUnidadeFederacao().getId()
		// .getUnidadeFederacao());
		//
		// cidadeEntity = repositoryCidade.findByUFCidade("RS", "PORTO ALEGRE");
		// assertNotNull(cidadeEntity);
		// assertEquals("PORTO ALEGRE", cidadeEntity.getDescricaoAbreviada());
		// assertEquals("RS", cidadeEntity.getUnidadeFederacao().getId()
		// .getUnidadeFederacao());
		//
		// cidadeEntity = repositoryCidade.findByUFCidade("SC", "JOINVILLE");
		// assertNotNull(cidadeEntity);
		// assertEquals("JOINVILLE", cidadeEntity.getDescricaoAbreviada());
		// assertEquals("SC", cidadeEntity.getUnidadeFederacao().getId()
		// .getUnidadeFederacao());
	}

	public DaoDepartment getDaoDepartment() {
		return daoDepartment;
	}

	public void setDaoDepartment(DaoDepartment daoDepartment) {
		this.daoDepartment = daoDepartment;
	}

}