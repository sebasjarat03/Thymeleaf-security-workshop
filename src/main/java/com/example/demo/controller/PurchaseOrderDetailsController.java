package com.example.demo.controller;

import java.util.Optional;

import com.example.demo.model.groups.Add;
import com.example.demo.model.prchasing.Purchaseorderdetail;
import com.example.demo.repositories.PurchaseOrderDetailRepository;
import com.example.demo.repositories.PurchaseOrderHeaderRepository;
import com.example.demo.services.PurchaseOrderDetailService;

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
@RequestMapping("/purchase-order-details")
public class PurchaseOrderDetailsController {

    private PurchaseOrderDetailService pods;
    private PurchaseOrderDetailRepository podr;
    private PurchaseOrderHeaderRepository pohr;

    @Autowired
    public PurchaseOrderDetailsController(PurchaseOrderDetailService pods, PurchaseOrderDetailRepository podr,
            PurchaseOrderHeaderRepository pohr) {
        this.pods = pods;
        this.podr = podr;
        this.pohr = pohr;
    }

    @GetMapping("")
    public String podIndex(Model model) {
        model.addAttribute("purchaseorderdetails", podr.findAll());
        return "purchase-order-details/index";
    }

    @GetMapping("/add")
    public String addTemplatePod(Model model) {
        model.addAttribute("purchaseorderdetail", new Purchaseorderdetail());
        model.addAttribute("purchaseorderheaders", pohr.findAll());

        return "purchase-order-details/add";
    }

    @PostMapping("/add")
    public String saveVendor(@Validated(Add.class) @ModelAttribute Purchaseorderdetail pod, BindingResult bindingResult,
            Model model, @RequestParam(value = "action", required = true) String action) {
        if (!action.equals("Cancel")) {
            if (bindingResult.hasErrors()) {
                model.addAttribute("purchaseorderdetail", pod);
                model.addAttribute("purchaseorderheaders", pohr.findAll());

                return "purchase-order-details/add";
            }

            pods.save(pod, pod.getPurchaseorderheader().getPurchaseorderid());
        }
        return "redirect:/purchase-order-details/";
    }

    @GetMapping("/edit/{id}")
    public String editTemplatePod(Model model, @PathVariable("id") Integer id) {
        Optional<Purchaseorderdetail> pod = podr.findById(id);
        if (!pod.isPresent()) {
            throw new IllegalArgumentException("Invalid id: " + id);
        }
        model.addAttribute("purchaseorderdetail", pod.get());
        model.addAttribute("purchaseorderheaders", pohr.findAll());

        return "/purchase-order-details/edit";
    }

    @PostMapping("/edit/{id}")
    public String editVendor(@Validated(Add.class) @ModelAttribute Purchaseorderdetail pod, BindingResult bindingResult,
            Model model, @PathVariable("id") Integer id,
            @RequestParam(value = "action", required = true) String action) {
        if (!action.equals("Cancel")) {
            if (bindingResult.hasErrors()) {
                model.addAttribute("purchaseorderdetail", pod);
                model.addAttribute("purchaseorderheaders", pohr.findAll());

                return "/purchase-order-details/edit";
            }

            pods.edit(pod, pod.getPurchaseorderheader().getPurchaseorderid());
        }
        return "redirect:/purchase-order-details/";
    }

    @GetMapping("/del/{id}")
    public String deletePod(@PathVariable Integer id, Model model) {
        Purchaseorderdetail pod = podr.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid id: " + id));
        podr.delete(pod);
        model.addAttribute("purchaseorderdetails", podr.findAll());
        return "redirect:/purchase-order-details/";
    }

    @GetMapping("/{id}")
    public String getPod(@PathVariable Integer id, Model model) {
        Purchaseorderdetail pod = podr.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid id: " + id));
        model.addAttribute("purchaseorderdetail", pod);

        return "purchase-order-details/info";
    }
}
