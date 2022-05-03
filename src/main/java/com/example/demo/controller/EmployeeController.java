package com.example.demo.controller;

import com.example.demo.model.hr.Employee;
import com.example.demo.repositories.EmployeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    private EmployeeRepository er;

    @Autowired
    public EmployeeController(EmployeeRepository er) {
        this.er = er;
    }

    @GetMapping("/{id}")
    public String getPoh(@PathVariable Integer id, Model model) {
        Employee em = er.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid id: " + id));
        model.addAttribute("employee", em);

        return "employee/info";
    }

}
