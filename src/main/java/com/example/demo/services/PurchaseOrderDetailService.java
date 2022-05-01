package com.example.demo.services;

import com.example.demo.model.prchasing.Purchaseorderdetail;

public interface PurchaseOrderDetailService {
    public boolean save(Purchaseorderdetail pod, Integer orderHId);

    public boolean edit(Purchaseorderdetail pod, Integer orderHId);

}
