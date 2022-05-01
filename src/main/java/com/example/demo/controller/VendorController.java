package com.example.demo.controller;

import java.util.Optional;

import com.example.demo.model.groups.Add;
import com.example.demo.model.prchasing.Vendor;
import com.example.demo.repositories.BusinessEntityRepository;
import com.example.demo.repositories.PurchaseOrderHeaderRepository;
import com.example.demo.repositories.VendorRepository;
import com.example.demo.services.VendorService;

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
@RequestMapping("/vendors")
public class VendorController {
    private VendorService vendorService;
    private VendorRepository vendorRepository;
    private PurchaseOrderHeaderRepository pohRepository;
    private BusinessEntityRepository ber;

    @Autowired
    public VendorController(VendorService vendorService, VendorRepository vendorRepository,
            PurchaseOrderHeaderRepository pohRepository, BusinessEntityRepository ber) {
        this.vendorService = vendorService;
        this.vendorRepository = vendorRepository;
        this.pohRepository = pohRepository;
        this.ber = ber;
    }

    @GetMapping("")
    public String vendorsIndex(Model model) {
        model.addAttribute("vendors", vendorRepository.findAll());
        return "/vendors/index";
    }

    @GetMapping("/add")
    public String addTemplateVendor(Model model) {
        model.addAttribute("vendor", new Vendor());
        // model.addAttribute("businessentities", ber.findAll());

        return "vendors/add";
    }

    @PostMapping("/add")
    public String saveVendor(@Validated(Add.class) @ModelAttribute Vendor vendor, BindingResult bindingResult,
            Model model, @RequestParam(value = "action", required = true) String action) {
        if (!action.equals("Cancel")) {
            if (bindingResult.hasErrors()) {
                model.addAttribute("vendor", vendor);
                // model.addAttribute("businessentities", ber.findAll());

                return "vendors/add";
            }

            vendorService.save(vendor);
        }
        return "redirect:/vendors/";
    }

    @GetMapping("/edit/{id}")
    public String editTemplateVendor(Model model, @PathVariable("id") Integer id) {
        Optional<Vendor> vendor = vendorRepository.findById(id);
        if (!vendor.isPresent()) {
            throw new IllegalArgumentException("Invalid id: " + id);
        }
        model.addAttribute("vendor", vendor.get());

        return "/vendors/edit";
    }

    @PostMapping("/edit/{id}")
    public String editVendor(@Validated(Add.class) @ModelAttribute Vendor vendor, BindingResult bindingResult,
            Model model, @PathVariable("id") Integer id,
            @RequestParam(value = "action", required = true) String action) {
        if (!action.equals("Cancel")) {
            if (bindingResult.hasErrors()) {
                model.addAttribute("vendor", vendor);
                // model.addAttribute("businessentities", ber.findAll());

                return "vendors/edit";
            }

            vendorService.edit(vendor, vendor.getBusinessentityid());
        }
        return "redirect:/vendors/";
    }

    @GetMapping("/del/{id}")
    public String deleteVendor(@PathVariable Integer id, Model model) {
        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid id: " + id));
        vendorRepository.delete(vendor);
        model.addAttribute("vendors", vendorRepository.findAll());
        return "redirect:/vendors/";
    }

}
