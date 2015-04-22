package com.github;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.dao.DaoDepartment;
import com.github.entity.Department;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/testCtxInitial.xml"})
//
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
//
@DatabaseSetup(value = {"/dbunit/department.dbunit.xml"})
public class TestDepartmentSpringManageTransaction extends AbstractTransactionalJUnit4SpringContextTests {

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
