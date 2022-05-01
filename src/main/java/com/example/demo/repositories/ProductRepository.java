package com.example.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.prod.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer>{

}
