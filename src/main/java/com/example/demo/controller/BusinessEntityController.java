package com.example.demo.controller;

import com.example.demo.model.person.Businessentity;
import com.example.demo.repositories.BusinessEntityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/business-entity")
public class BusinessEntityController {
    private BusinessEntityRepository ber;

    @Autowired
    public BusinessEntityController(BusinessEntityRepository ber) {
        this.ber = ber;
    }

    @GetMapping("/{id}")
    public String getPoh(@PathVariable Integer id, Model model) {
        Businessentity be = ber.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid id: " + id));
        model.addAttribute("businessentity", be);

        return "business-entity/info";
    }
}
