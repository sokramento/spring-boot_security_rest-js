package ru.kata.spring.boot_security.bootstrap.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.bootstrap.repositories.RoleRepository;
import ru.kata.spring.boot_security.bootstrap.service.UserService;

import java.security.Principal;



@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleRepository roleRepository;

    @Autowired
    public AdminController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping()
    public String getUsers( Model model, Principal principal) {
        model.addAttribute("user", userService.findByEmail(principal.getName()));
        model.addAttribute("users", userService.allUsers());
        model.addAttribute("roles", roleRepository.findAll());
        return "index";
    }

    @GetMapping("/info")
    public String pageForUser (Model model, Principal principal) {
        model.addAttribute("user",userService.findByEmail(principal.getName()));
        return "admin_info";
    }
}