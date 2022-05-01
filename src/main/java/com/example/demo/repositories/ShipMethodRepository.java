package com.example.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.prchasing.Shipmethod;

@Repository
public interface ShipMethodRepository extends CrudRepository<Shipmethod, Integer>{

}
