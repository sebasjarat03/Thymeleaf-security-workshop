package com.example.demo.services;

import java.math.BigDecimal;
import java.util.Optional;

import com.example.demo.model.prchasing.Shipmethod;
import com.example.demo.repositories.ShipMethodRepository;

import org.springframework.beans.factory.annotation.Autowired;

public class ShipMethodServiceImp implements ShipMethodService {

    ShipMethodRepository smr;

    @Autowired
    public ShipMethodServiceImp(ShipMethodRepository smr) {
        this.smr = smr;
    }

    @Override
    public boolean save(Shipmethod sm) {
        boolean saved = false;
        if (sm == null) {
            throw new IllegalArgumentException("The ship method must not be null");
        } else if (sm.getShipbase().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("The ship base must be greater than zero");
        } else if (sm.getShiprate().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("The ship rate must be greater than zero");
        } else if (sm.getName().length() < 4) {
            throw new IllegalArgumentException("The name must be at least 4 characters long");
        } else {
            smr.save(sm);
            saved = true;
        }
        return saved;
    }

    @Override
    public boolean edit(Shipmethod sm) {
        boolean edited = false;
        if (sm == null) {
            throw new IllegalArgumentException("The ship method must not be null");
        } else {
            Optional<Shipmethod> shipm = smr.findById(sm.getShipmethodid());
            if (sm.getShipbase().compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("The ship base must be greater than zero");
            } else if (sm.getShiprate().compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("The ship rate must be greater than zero");
            } else if (sm.getName().length() < 4) {
                throw new IllegalArgumentException("The name must be at least 4 characters long");
            } else if (shipm.isEmpty()) {
                throw new IllegalArgumentException("The ship methos doesn't exists");
            } else {
                Shipmethod s = shipm.get();
                s.setModifieddate(sm.getModifieddate());
                s.setName(sm.getName());
                s.setPurchaseorderheaders(sm.getPurchaseorderheaders());
                s.setRowguid(sm.getRowguid());
                s.setShipbase(sm.getShipbase());
                s.setShipmethodid(sm.getShipmethodid());
                s.setShiprate(sm.getShiprate());
                smr.save(s);
                edited = true;
            }
        }
        return edited;
    }

}
