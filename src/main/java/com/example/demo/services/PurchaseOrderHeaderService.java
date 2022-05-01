package com.example.demo.services;

import com.example.demo.model.prchasing.Purchaseorderheader;

public interface PurchaseOrderHeaderService {
    public boolean save(Purchaseorderheader poh, Integer personId, Integer employeeId);

    public boolean edit(Purchaseorderheader poh, Integer personId, Integer employeeId);
}
