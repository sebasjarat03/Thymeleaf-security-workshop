package com.example.demo.controller;

import java.util.Optional;

import com.example.demo.model.groups.Add;
import com.example.demo.model.prchasing.Purchaseorderheader;
import com.example.demo.repositories.EmployeeRepository;
import com.example.demo.repositories.PurchaseOrderHeaderRepository;
import com.example.demo.services.PurchaseOrderHeaderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/purchase-order-headers")
public class PurchaseOrderHeaderController {
    private PurchaseOrderHeaderService pohs;
    private PurchaseOrderHeaderRepository pohr;
    private EmployeeRepository er;

    @Autowired
    public PurchaseOrderHeaderController(PurchaseOrderHeaderService pohs, PurchaseOrderHeaderRepository pohr,
            EmployeeRepository er) {
        this.pohs = pohs;
        this.pohr = pohr;
        this.er = er;
    }

    @GetMapping("")
    public String pohIndex(Model model) {
        model.addAttribute("purchaseorderheaders", pohr.findAll());
        return "purchase-order-headers/index";
    }

    @GetMapping("/add")
    public String addTemplatePoh(Model model) {
        model.addAttribute("purchaseorderheader", new Purchaseorderheader());
        model.addAttribute("employees", er.findAll());

        return "purchase-order-headers/add";
    }

    @PostMapping("/add")
    public String savePoh(@Validated(Add.class) @ModelAttribute Purchaseorderheader poh, BindingResult bindingResult,
            Model model, @RequestParam(value = "action", required = true) String action) {
        if (!action.equals("Cancel")) {
            if (bindingResult.hasErrors()) {
                model.addAttribute("purchaseorderheader", poh);
                model.addAttribute("employees", er.findAll());

                return "purchase-order-headers/add";
            }

            pohs.save(poh, 0, poh.getEmployeeid());
        }
        return "redirect:/purchase-order-headers/";
    }

    @GetMapping("/edit/{id}")
    public String editTemplatePoh(Model model, @PathVariable("id") Integer id) {
        Optional<Purchaseorderheader> poh = pohr.findById(id);
        if (!poh.isPresent()) {
            throw new IllegalArgumentException("Invalid id: " + id);
        }
        model.addAttribute("purchaseorderheader", poh.get());
        model.addAttribute("employees", er.findAll());

        return "/purchase-order-headers/edit";
    }

    @PostMapping("/edit/{id}")
    public String editPoh(@Validated(Add.class) @ModelAttribute Purchaseorderheader poh, BindingResult bindingResult,
            Model model, @PathVariable("id") Integer id,
            @RequestParam(value = "action", required = true) String action) {
        if (!action.equals("Cancel")) {
            if (bindingResult.hasErrors()) {
                model.addAttribute("purchaseorderheader", poh);
                model.addAttribute("employees", er.findAll());

                return "/purchase-order-headers/edit";
            }

            pohs.edit(poh, 0, poh.getEmployeeid());
        }
        return "redirect:/purchase-order-headers/";
    }

    @GetMapping("/del/{id}")
    public String deletePoh(@PathVariable Integer id, Model model) {
        Purchaseorderheader poh = pohr.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid id: " + id));
        pohr.delete(poh);
        model.addAttribute("purchaseorderheaders", pohr.findAll());
        return "redirect:/purchase-order-headers/";
    }

    @GetMapping("/{id}")
    public String getPoh(@PathVariable Integer id, Model model) {
        Purchaseorderheader poh = pohr.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid id: " + id));
        model.addAttribute("purchaseorderheader", poh);

        return "purchase-order-headers/info";
    }

}
