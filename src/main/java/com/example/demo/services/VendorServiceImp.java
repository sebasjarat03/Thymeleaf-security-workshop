package com.example.demo.services;

import java.util.Optional;

import com.example.demo.model.person.Businessentity;
import com.example.demo.model.prchasing.Vendor;
import com.example.demo.repositories.BusinessEntityRepository;
import com.example.demo.repositories.VendorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VendorServiceImp implements VendorService {

    VendorRepository vr;
    BusinessEntityRepository ber;

    @Autowired
    public VendorServiceImp(VendorRepository vr, BusinessEntityRepository ber) {

        this.vr = vr;
        this.ber = ber;
    }

    @Override
    public boolean save(Vendor vendor) {
        boolean saved = false;
        Businessentity be = new Businessentity();
        ber.save(be);

        if (vendor == null) {
            throw new IllegalArgumentException("vendor cannot be null");
        } else if (!(vendor.getCreditrating() > 0)) {
            throw new IllegalArgumentException("Credit rating must be greater than zero");
        } else if (!vendor.getPurchasingwebserviceurl().startsWith("https")) {
            throw new IllegalArgumentException("Purchasing web service url must start with https");
        } else if (vendor.getName() == null) {
            throw new IllegalArgumentException("Name must not be null");
        } else {
            vendor.setBusinessentityid(be.getBusinessentityid());
            vr.save(vendor);
            saved = true;

        }
        return saved;
    }

    @Override
    public boolean edit(Vendor vendor, Integer businessEntityId) {
        boolean edited = false;
        Optional<Businessentity> businessEntity = ber.findById(businessEntityId);

        if (vendor == null) {
            throw new IllegalArgumentException("Vendor cannot be null");
        } else {
            Optional<Vendor> v = vr.findById(vendor.getBusinessentityid());
            if (businessEntity.isEmpty()) {
                throw new IllegalArgumentException("Business entity doesn't exists");
            } else if (!(vendor.getCreditrating() > 0)) {
                throw new IllegalArgumentException("Credit rating must be greater than zero");
            } else if (!vendor.getPurchasingwebserviceurl().startsWith("https")) {
                throw new IllegalArgumentException("Purchasing web service url must start with https");
            } else if (vendor.getName() == null) {
                throw new IllegalArgumentException("Name must not be null");
            } else if (v.isEmpty()) {
                throw new IllegalArgumentException("Vendor doesn't exists");
            } else {
                Vendor nv = v.get();
                nv.setAccountnumber(vendor.getAccountnumber());
                nv.setActiveflag(vendor.getActiveflag());
                nv.setBusinessentityid(vendor.getBusinessentityid());
                nv.setCreditrating(vendor.getCreditrating());
                nv.setModifieddate(vendor.getModifieddate());
                nv.setName(vendor.getName());
                nv.setPreferredvendorstatus(vendor.getPreferredvendorstatus());
                nv.setProductvendors(vendor.getProductvendors());
                nv.setPurchaseorderheaders(vendor.getPurchaseorderheaders());
                nv.setPurchasingwebserviceurl(vendor.getPurchasingwebserviceurl());
                vr.save(nv);
                edited = true;
            }
        }
        return edited;
    }

}
