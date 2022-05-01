package com.example.demo.services;

import java.math.BigDecimal;
import java.util.Optional;

import com.example.demo.model.prchasing.Purchaseorderdetail;
import com.example.demo.model.prchasing.Purchaseorderheader;
import com.example.demo.repositories.PurchaseOrderDetailRepository;
import com.example.demo.repositories.PurchaseOrderHeaderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseOrderDetailServiceImp implements PurchaseOrderDetailService {

    PurchaseOrderDetailRepository podr;
    PurchaseOrderHeaderRepository pohr;

    @Autowired
    public PurchaseOrderDetailServiceImp(PurchaseOrderDetailRepository podr, PurchaseOrderHeaderRepository pohr) {
        this.podr = podr;
        this.pohr = pohr;
    }

    @Override
    public boolean save(Purchaseorderdetail pod, Integer orderHId) {
        boolean created = false;
        Optional<Purchaseorderheader> header = pohr.findById(orderHId);
        if (pod == null) {
            throw new IllegalArgumentException("purchase order detail must not be null");
        } else if (pod.getOrderqty() < 0) {
            throw new IllegalArgumentException("purchase order quantity must be greater than 0");
        } else if (pod.getUnitprice().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("unit price must be greater than zero");
        } else if (header.isEmpty()) {
            throw new IllegalArgumentException("purchase order header doesn't exits");
        } else {
            pod.setPurchaseorderheader(header.get());
            podr.save(pod);
            created = true;
        }

        return created;
    }

    @Override
    public boolean edit(Purchaseorderdetail pod, Integer orderHId) {
        boolean edited = false;
        Optional<Purchaseorderheader> header = pohr.findById(orderHId);
        if (pod == null) {
            throw new IllegalArgumentException("purchase order detail must not be null");
        } else {
            Optional<Purchaseorderdetail> purd = podr.findById(pod.getId());
            if (pod.getOrderqty() < 0) {
                throw new IllegalArgumentException("purchase order quantity must be greater than 0");
            } else if (pod.getUnitprice().compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("unit price must be greater than zero");
            } else if (header.isEmpty()) {
                throw new IllegalArgumentException("purchase order header doesn't exits");
            } else {
                Purchaseorderdetail np = purd.get();
                np.setDuedate(pod.getDuedate());
                np.setId(pod.getId());
                np.setModifieddate(pod.getModifieddate());
                np.setOrderqty(pod.getOrderqty());
                np.setProductid(pod.getProductid());
                np.setPurchaseorderheader(header.get());
                np.setReceivedqty(pod.getReceivedqty());
                np.setRejectedqty(pod.getRejectedqty());
                np.setUnitprice(pod.getUnitprice());
                podr.save(np);
                edited = true;
            }
        }
        return edited;
    }

}
