package com.github.entity;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "Department")
@Table(name = "Department")
@Cacheable(true)
public class Department implements Serializable, Comparable<Department> {

	private static final long serialVersionUID = 1L;

	@Id
	@Column
	private Long id;

	private String name;

	private Boolean active;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Override
	public int compareTo(Department o) {
		return id.compareTo(o.id);
	}

}
