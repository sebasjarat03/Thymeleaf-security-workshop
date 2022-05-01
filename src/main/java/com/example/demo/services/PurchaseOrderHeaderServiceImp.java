package com.example.demo.services;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import com.example.demo.model.hr.Employee;
import com.example.demo.model.person.Person;
import com.example.demo.model.prchasing.Purchaseorderheader;
import com.example.demo.repositories.EmployeeRepository;
import com.example.demo.repositories.PersonRepository;
import com.example.demo.repositories.PurchaseOrderHeaderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseOrderHeaderServiceImp implements PurchaseOrderHeaderService {
    PurchaseOrderHeaderRepository pohr;
    PersonRepository pr;
    EmployeeRepository er;

    @Autowired
    public PurchaseOrderHeaderServiceImp(PurchaseOrderHeaderRepository pohr, PersonRepository pr,
            EmployeeRepository er) {
        this.pohr = pohr;
        this.pr = pr;
        this.er = er;
    }

    @Override
    public boolean save(Purchaseorderheader poh, Integer personId, Integer employeeId) {
        boolean created = false;
        Optional<Person> person = pr.findById(personId);
        Optional<Employee> employee = er.findById(employeeId);
        if (poh == null) {
            throw new IllegalArgumentException("purchase order header is null");
        } else if (!poh.getOrderdate().toString()
                .startsWith(Timestamp.valueOf(LocalDateTime.now()).toString().split(" ")[0])) {
            throw new IllegalArgumentException("the order date must be the actual date");
        } else if (poh.getSubtotal().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("The subtotal must be greater than zero");
        } else if (person.isEmpty()) {
            throw new IllegalArgumentException("the person does not exist");
        } else if (employee.isEmpty()) {
            throw new IllegalArgumentException("the employee does not exist");
        } else {
            poh.setEmployeeid(employeeId);
            pohr.save(poh);
            created = true;
        }

        return created;
    }

    @Override
    public boolean edit(Purchaseorderheader poh, Integer personId, Integer employeeId) {
        boolean edited = false;
        Optional<Person> person = pr.findById(personId);
        Optional<Employee> employee = er.findById(employeeId);
        if (poh == null) {
            throw new IllegalArgumentException("purchase order header is null");
        } else {
            Optional<Purchaseorderheader> phead = pohr.findById(poh.getPurchaseorderid());
            if (!poh.getOrderdate().toString()
                    .startsWith(Timestamp.valueOf(LocalDateTime.now()).toString().split(" ")[0])) {
                throw new IllegalArgumentException("the order date must be the actual date");
            } else if (poh.getSubtotal().compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("The subtotal must be greater than zero");
            } else if (person.isEmpty()) {
                throw new IllegalArgumentException("the person does not exist");
            } else if (employee.isEmpty()) {
                throw new IllegalArgumentException("the employee does not exist");
            } else {
                Purchaseorderheader nh = phead.get();
                nh.setEmployeeid(employeeId);
                nh.setFreight(poh.getFreight());
                nh.setModifieddate(poh.getModifieddate());
                nh.setOrderdate(poh.getOrderdate());
                nh.setPurchaseorderdetails(poh.getPurchaseorderdetails());
                nh.setPurchaseorderid(poh.getPurchaseorderid());
                nh.setRevisionnumber(poh.getRevisionnumber());
                nh.setShipdate(poh.getShipdate());
                nh.setShipmethod(poh.getShipmethod());
                nh.setStatus(poh.getStatus());
                nh.setSubtotal(poh.getSubtotal());
                nh.setTaxamt(poh.getTaxamt());
                nh.setVendor(poh.getVendor());
                pohr.save(nh);
                edited = true;
            }
        }
        return edited;
    }

}
