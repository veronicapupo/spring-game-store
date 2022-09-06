package com.store.games.controller;

import com.store.games.model.Category;
import com.store.games.model.Product;
import com.store.games.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/category")
@CrossOrigin
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;


    @GetMapping
    public ResponseEntity<List<Category>> getAll(){
        return ResponseEntity.ok(categoryRepository.findAll());

    }
    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(@PathVariable Long id){
        return categoryRepository.findById(id)
                .map(res-> ResponseEntity.ok(res))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Category>> getByType(@PathVariable String type){
     return ResponseEntity.ok(categoryRepository.findAllByTypeContainingIgnoreCase(type));
    }
    @PostMapping
    public ResponseEntity<Category> postCategory(@Valid @RequestBody Category category){
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryRepository.save(category));

    }
    @PutMapping
    public ResponseEntity<Category> putCategory(@Valid @RequestBody Category category){
        return categoryRepository.findById(category.getId())
                .map(res-> new ResponseEntity(categoryRepository.save(category), HttpStatus.OK))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty())
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND);
        categoryRepository.deleteById(id);
    }
}
