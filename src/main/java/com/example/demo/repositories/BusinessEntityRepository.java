package com.example.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.person.Businessentity;

@Repository
public interface BusinessEntityRepository extends CrudRepository<Businessentity, Integer> {

}
