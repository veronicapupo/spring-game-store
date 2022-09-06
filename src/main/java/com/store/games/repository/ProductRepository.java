package com.store.games.repository;

import com.store.games.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

     List<Product> findAllByNameContainingIgnoreCase(@Param("name")String name);
}
