package com.specgraph.entitygraph.repository;

import com.specgraph.entitygraph.model.Characteristic;
import com.specgraph.entitygraph.repository.specentitygraph.CharacteristicsJpaSpecificationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql(scripts = "/entitygraph-data.sql")
public class CharacteristicsJpaSpecificationRepositoryTest {

    @Autowired
    private CharacteristicsJpaSpecificationRepository characteristicsJpaSpecificationRepository;

    @Test
    public void find() {
        int pageNumber = 1;
        int pageSize = 10;
        PageRequest pageable = PageRequest.of(pageNumber, pageSize);

        Page<Characteristic> page =
            characteristicsJpaSpecificationRepository.findByTypeUsingSpecification("Rigid", pageable);

        assertThat(page.getTotalElements()).isEqualTo(1);
    }

}