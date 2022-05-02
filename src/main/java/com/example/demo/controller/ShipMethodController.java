package com.example.demo.controller;

import java.util.Optional;

import com.example.demo.model.groups.Add;
import com.example.demo.model.prchasing.Shipmethod;
import com.example.demo.repositories.ShipMethodRepository;
import com.example.demo.services.ShipMethodService;

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
@RequestMapping("/ship-methods")
public class ShipMethodController {
    private ShipMethodService shipMethodService;
    private ShipMethodRepository shipMethodRepository;

    @Autowired
    public ShipMethodController(ShipMethodService shipMethodService, ShipMethodRepository shipMethodRepository) {
        this.shipMethodService = shipMethodService;
        this.shipMethodRepository = shipMethodRepository;
    }

    @GetMapping("")
    public String shipmethodIndex(Model model) {
        model.addAttribute("shipmethods", shipMethodRepository.findAll());
        return "ship-methods/index";
    }

    @GetMapping("/add")
    public String addTemplateShipmethod(Model model) {
        model.addAttribute("shipmethod", new Shipmethod());

        return "/ship-methods/add";
    }

    @PostMapping("/add")
    public String save(@Validated(Add.class) @ModelAttribute Shipmethod shipmethod, BindingResult bindingResult,
            Model model, @RequestParam(value = "action", required = true) String action) {
        if (!action.equals("Cancel")) {
            if (bindingResult.hasErrors()) {
                model.addAttribute("shipmethod", shipmethod);
                // model.addAttribute("businessentities", ber.findAll());

                return "ship-methods/add";
            }

            shipMethodService.save(shipmethod);
        }
        return "redirect:/ship-methods/";
    }

    @GetMapping("/edit/{id}")
    public String editTemplateShipmethod(Model model, @PathVariable("id") Integer id) {
        Optional<Shipmethod> shipmethod = shipMethodRepository.findById(id);
        if (!shipmethod.isPresent()) {
            throw new IllegalArgumentException("Invalid id: " + id);
        }
        model.addAttribute("shipmethod", shipmethod.get());

        return "/ship-methods/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@Validated(Add.class) @ModelAttribute Shipmethod shipmethod, BindingResult bindingResult,
            Model model, @PathVariable("id") Integer id,
            @RequestParam(value = "action", required = true) String action) {
        if (!action.equals("Cancel")) {
            if (bindingResult.hasErrors()) {
                model.addAttribute("shipmethod", shipmethod);

                return "ship-methods/edit";
            }

            shipMethodService.edit(shipmethod);
        }
        return "redirect:/ship-methods/";
    }

    @GetMapping("/del/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        Shipmethod shipmethod = shipMethodRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid id: " + id));
        shipMethodRepository.delete(shipmethod);
        model.addAttribute("shipmethods", shipMethodRepository.findAll());
        return "redirect:/ship-methods/";
    }

    @GetMapping("/{id}")
    public String get(@PathVariable Integer id, Model model) {
        Shipmethod shipmethod = shipMethodRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid id: " + id));
        model.addAttribute("shipmethod", shipmethod);

        return "ship-methods/info";
    }

}
