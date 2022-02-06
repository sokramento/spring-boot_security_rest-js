package ru.kata.spring.boot_security.bootstrap.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.bootstrap.models.Role;
import ru.kata.spring.boot_security.bootstrap.models.User;
import ru.kata.spring.boot_security.bootstrap.repositories.RoleRepository;
import ru.kata.spring.boot_security.bootstrap.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Set;

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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getUsers( Model model, Principal principal) {
        model.addAttribute("user", userService.findByEmail(principal.getName()));
        model.addAttribute("users", userService.allUsers());
        return "index";
    }

    @GetMapping(value = "/new")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String newPerson (@ModelAttribute("user") User user, Model model){
        model.addAttribute("listRoles", roleRepository.findAll());
        return "new";
    }

    @PostMapping ()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @SuppressWarnings("unchecked")
    public String create(@ModelAttribute("user") User user,
                         @RequestParam("listRoles") ArrayList<Integer>roles) {
        user.setRoles((Set<Role>) roleRepository.findAllById(roles));
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("user", userService.getById(id));
        model.addAttribute("listRoles", roleRepository.findAll());
        return "edit";
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @SuppressWarnings("unchecked")
    public String update(@ModelAttribute("user") User user,
                         @PathVariable("id") int id,
                         @RequestParam("listRoles") ArrayList<Integer>roles){
        user.setRoles((Set<Role>) roleRepository.findAllById(roles));
        userService.edit(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@PathVariable("id") int id){
        userService.deleteById(id);
        return "redirect:/admin";
    }

    @GetMapping("/info")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String pageForUser (Model model, Principal principal) {
        model.addAttribute("user",userService.findByEmail(principal.getName()));
        return "admin_info";
    }
}
