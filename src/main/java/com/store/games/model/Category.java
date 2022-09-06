package com.store.games.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Category {


    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String type;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("category")
    private List <Product> product;

    public List<Product> getProduct() {
        return product;
    }

    public void setProducts(List<Product> products) {
        this.product = products;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
