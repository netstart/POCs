package com.github.dao;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.entity.Department;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class DaoDepartment extends DaoHibernate<Department, Long> {

	private static final long serialVersionUID = 1406565372959939641L;

	public DaoDepartment() {
		super(Department.class);
	}

}
