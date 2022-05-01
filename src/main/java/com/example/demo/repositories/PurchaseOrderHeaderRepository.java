package com.example.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.prchasing.Purchaseorderheader;

@Repository
public interface PurchaseOrderHeaderRepository extends CrudRepository<Purchaseorderheader, Integer> {

}
