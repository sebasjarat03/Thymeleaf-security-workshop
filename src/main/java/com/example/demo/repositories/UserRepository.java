package com.example.demo.repositories;

import com.example.demo.model.Userr;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<Userr, Long> {
    Userr findByUsername(String username);
}
