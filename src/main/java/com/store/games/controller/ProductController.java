package com.store.games.controller;

import com.store.games.model.Product;
import com.store.games.repository.CategoryRepository;
import com.store.games.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping
    public ResponseEntity<List<Product>> getAll(){
        return ResponseEntity.ok(productRepository.findAll());

    }
    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id){
        return productRepository.findById(id)
                .map(res-> ResponseEntity.ok(res))
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<Product> postProduct(@Valid @RequestBody Product product){
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(product));

    }
    @PutMapping
    public ResponseEntity<Product> putProduct(@Valid @RequestBody Product product){
        return productRepository.findById(product.getId())
                .map(res-> new ResponseEntity(productRepository.save(product), HttpStatus.OK))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty())
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND);
        productRepository.deleteById(id);
    }

}
