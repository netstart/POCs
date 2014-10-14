package com.github.netstart.view.dto;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class PearsonDTO {

	private String name;
	private Date nascimento;
	private BigDecimal salary;

	private Boolean hasGitHubProfile;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getNascimento() {
		return nascimento;
	}

	public void setNascimento(Date nascimento) {
		this.nascimento = nascimento;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	public Boolean getHasGitHubProfile() {
		return hasGitHubProfile;
	}

	public void setHasGitHubProfile(Boolean hasGitHubProfile) {
		this.hasGitHubProfile = hasGitHubProfile;
	}

	public String toString() {
		return toString(ToStringStyle.MULTI_LINE_STYLE);
	}
	
	public String toString(ToStringStyle style){
		return ReflectionToStringBuilder.toString(this, style);
	}
}
