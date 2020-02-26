package com.github.netstart.scrap.graphql;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.github.netstart.scrap.model.ChangePlan;
import com.github.netstart.scrap.service.ChangePlanService;

@Component
public class ChangePlanGraphQL implements GraphQLQueryResolver {
	private List<String> smartMei = Arrays.asList("https://www.smartmei.com.br", "www.smartmei.com.br", "smartmei.com.br");

	@Autowired
	private ChangePlanService changePlanService;

	public ChangePlan findChangePlan(String site) {
		if (!smartMei.contains(site)) {
			throw new IllegalArgumentException("Apenas é permitido os sites " + smartMei);
		}

		return changePlanService.findProfetionalPlan();
	}

}
