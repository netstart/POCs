package com.specgraph.entitygraph.repository;
import com.specgraph.entitygraph.model.Characteristic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacteristicsRepository extends JpaRepository<Characteristic, Long> {
    
    @EntityGraph(attributePaths = {"item"})
    Characteristic findByType(String type);
    
//    Characteristic findByType(String type); 
    
    @EntityGraph(attributePaths = {"item"})
    Page<Characteristic> findAll(Specification<Characteristic> specification, Pageable pageable);
    
}