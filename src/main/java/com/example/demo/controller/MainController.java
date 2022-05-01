package com.example.demo.controller;

import com.example.demo.model.Userr;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class MainController {

    // @GetMapping("")
    // public String index(){
    // return "/index.html";
    // }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new Userr());
        return "/login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "/logout";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "/access-denied";
    }

}
