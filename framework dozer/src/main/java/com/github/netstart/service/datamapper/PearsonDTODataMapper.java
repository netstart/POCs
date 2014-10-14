package com.github.netstart.service.datamapper;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.netstart.model.entity.PearsonBean;
import com.github.netstart.view.dto.PearsonDTO;

public class PearsonDTODataMapper {

	private static final Log log = LogFactory.getLog(PearsonDTODataMapper.class);

	@Autowired
	private Mapper mapper;

	public PearsonBean getPearsonBeanFromDTO(PearsonDTO dto) {
		log.debug("dto:" + dto.toString());

		PearsonBean PearsonBean = (PearsonBean) mapper.map(dto, PearsonBean.class);
		log.debug("\nPearsonBean:" + ToStringBuilder.reflectionToString(PearsonBean, ToStringStyle.MULTI_LINE_STYLE));

		return PearsonBean;
	}

}
