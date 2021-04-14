package com.specgraph.entitygraph;

import com.specgraph.entitygraph.model.Item;
import com.specgraph.entitygraph.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql(scripts = "/entitygraph-data.sql")
public class ItemTest {

    @Autowired
    private ItemRepository itemRepo;

    @Test
    public void find() {
        Item item = itemRepo.findByName("Table");
        assertThat(item.getId()).isEqualTo(1L);
    }

}