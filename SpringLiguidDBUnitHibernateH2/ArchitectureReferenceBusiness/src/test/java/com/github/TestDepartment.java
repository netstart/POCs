package com.github;

import java.util.List;

import javax.servlet.jsp.tagext.TryCatchFinally;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import static org.junit.Assert.*;

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
@DatabaseSetup(value = { "/dbunit/department.dbunit.xml" })
@DatabaseTearDown(value = { "/dbunit/department.dbunit.xml" }, type = DatabaseOperation.DELETE_ALL)
public class TestDepartment {

	@Autowired
	private DaoDepartment daoDepartment;

	@Test
	public void findDepartment() throws Exception {
		assertNotNull("Spring do not inject DaoDepartment", daoDepartment);
		assertTrue("sessionFactory is null, it´s crazy man!",
				daoDepartment.containSessionFactory());

		List<Department> findAll = daoDepartment.findAll();
		
		assertEquals("Shoud be one register!", 1, findAll.size());
		
		Department department = findAll.get(0);
		assertEquals("Developer deparment", department.getName());
		assertEquals(new Long(1), department.getId());
		assertTrue(department.getActive());
		
		
	}

	public DaoDepartment getDaoDepartment() {
		return daoDepartment;
	}

	public void setDaoDepartment(DaoDepartment daoDepartment) {
		this.daoDepartment = daoDepartment;
	}

}