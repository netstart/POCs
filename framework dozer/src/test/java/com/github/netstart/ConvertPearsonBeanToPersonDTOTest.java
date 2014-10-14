package com.github.netstart;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dozer.Mapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.netstart.model.entity.PearsonBean;
import com.github.netstart.view.dto.PearsonDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/dozer-datamapper-config.xml" })
public class ConvertPearsonBeanToPersonDTOTest {

	private static final Log log = LogFactory.getLog(ConvertPearsonBeanToPersonDTOTest.class);

	@Autowired
	private Mapper mapper;

	@Test
	public void runTestDtoToDomain() {
		log.debug("Dto-->Domain Mapper Test");

		PearsonDTO dto = buildPearsonDTO();

		String expected = "PearsonDTO[name=The Jocker,nascimento=<null>,salary=100.50,hasGitHubProfile=false]";
		String actual = dto.toString(ToStringStyle.SHORT_PREFIX_STYLE);
		Assert.assertEquals(expected, actual);

		PearsonBean pearson = (PearsonBean) mapper.map(dto, PearsonBean.class);
		expected = "PearsonBean[name=The Jocker,nascimento=<null>,salary=100.50]";
		actual = pearson.toString(ToStringStyle.SHORT_PREFIX_STYLE);
		Assert.assertEquals(expected, actual);
	}

	private PearsonDTO buildPearsonDTO() {
		PearsonDTO dto = new PearsonDTO();
		dto.setName("The Jocker");
		dto.setSalary(new BigDecimal("100.50"));
		dto.setHasGitHubProfile(Boolean.FALSE);
		return dto;
	}

}
