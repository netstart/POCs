package com.specgraph.entitygraph.repository;

import com.specgraph.entitygraph.model.Characteristic;
import com.specgraph.entitygraph.repository.CharacteristicsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
//@RunWith(SpringRunner.class)
@Sql(scripts = "/entitygraph-data.sql")
public class CharacteristicTest {

    @Autowired
    private CharacteristicsRepository characteristicsRepo;

    @Test
    public void find() {
        Characteristic characteristic = characteristicsRepo.findByType("Rigid");
        assertThat(characteristic.getId()).isEqualTo(1L);
    }

}