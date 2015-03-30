package com.github;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/testCtxInitial.xml" })
// @TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
// DbUnitTestExecutionListener.class })
// @DatabaseSetup(value = { "/dbunit/facade/parametroGeral.dbunit.xml" })
// @DatabaseTearDown(value = { "/dbunit/facade/parametroGeral.dbunit.xml" },
// type = DatabaseOperation.DELETE_ALL)
public class TestFacadeUtils {

	@Test
	public void test() {

	}

}