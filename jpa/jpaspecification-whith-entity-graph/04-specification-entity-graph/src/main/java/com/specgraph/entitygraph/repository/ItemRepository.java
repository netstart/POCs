package com.specgraph.entitygraph.repository;
import com.specgraph.entitygraph.model.Item;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @EntityGraph(value = "Item.characteristics", type = EntityGraphType.FETCH)
    Item findByName(String name);
}