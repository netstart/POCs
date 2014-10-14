package com.github.netstart;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.netstart.model.entity.PearsonBean;
import com.github.netstart.service.datamapper.PearsonDTODataMapper;
import com.github.netstart.view.dto.PearsonDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/dozer-datamapper-config.xml" })
public class FundingRequestDataMapperTest {

	private static final Log log = LogFactory.getLog(FundingRequestDataMapperTest.class);

	@Autowired
	private PearsonDTODataMapper dataMapper;

	@Test
	public void runTestDtoToDomain() {
		log.debug("Dto-->Domain Mapper Test");

		PearsonDTO dto = buildPearsonDTO();
		PearsonBean pearsonBean = (PearsonBean) dataMapper.getPearsonBeanFromDTO(dto);

		String expected = "PearsonBean[name=The Jocker,nascimento=<null>,salary=100.50]";
		String actual = pearsonBean.toString(ToStringStyle.SHORT_PREFIX_STYLE);
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
