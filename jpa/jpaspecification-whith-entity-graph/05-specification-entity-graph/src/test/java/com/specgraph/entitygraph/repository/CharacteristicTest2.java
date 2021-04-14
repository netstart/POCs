package com.specgraph.entitygraph.repository;

import com.specgraph.entitygraph.model.Characteristic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql(scripts = "/entitygraph-data.sql")
public class CharacteristicTest2 {

    @Autowired
    private CharacteristicsRepository characteristicsRepo;

    @Test
    public void findUsingCustomSpecification() {

        int pageNumber = 1;
        int pageSize = 10;
        PageRequest pageable = PageRequest.of(pageNumber, pageSize);

        Specification<Characteristic> spec =
            (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("type"), "Rigid");

        System.out.println("FIND BY ID");
        Optional<Characteristic> findById = characteristicsRepo.findById(1L);

        System.out.println("FIND ALL");
        Page<Characteristic> page = characteristicsRepo.findAll(spec, pageable);

        assertThat(page.getTotalElements()).isEqualTo(1);
    }

}