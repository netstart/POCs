package com.github;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
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

/**
 * https://github.com/lkrnac/blog-2014-01-21-mock-autowired-fields
 * 
 * http://java.dzone.com/articles/use-mockito-mock-autowired
 * 
 * @author claytonpassos
 *
 *         This example, demonstrate how do you integrate Spring test, DBUnit,
 *         H2 data base, powermock and mockito to test your application
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/testCtxInitial.xml"})
//
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
//
@DatabaseSetup(value = {"/dbunit/department.dbunit.xml"})
@DatabaseTearDown(value = {"/dbunit/department.dbunit.xml"}, type = DatabaseOperation.DELETE_ALL)
public class DepartmentWithMockTest {

    // Create Mock
    @Mock
    private DaoDepartment daoDepartment;;

    /*
     * 
     * Inject mock into serviceDepartment
     * 
     * @InjectMocks
     * 
     * @Autowired private ServiceDepartment serviceDepartment
     */

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findDepartment() throws Exception {
        assertNotNull("Spring do not inject DaoDepartment", daoDepartment);

        List<Department> all = new ArrayList<Department>();
        all.add(new Department(new Long(1), "Developer deparment", true));
        Mockito.when(daoDepartment.findAll()).thenReturn(all);

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
