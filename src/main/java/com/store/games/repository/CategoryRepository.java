package com.store.games.repository;

import com.store.games.model.Category;
import com.store.games.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByTypeContainingIgnoreCase(@Param("type")String type);

}
