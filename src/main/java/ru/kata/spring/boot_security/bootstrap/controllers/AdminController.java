package ru.kata.spring.boot_security.bootstrap.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.bootstrap.models.User;
import ru.kata.spring.boot_security.bootstrap.repositories.RoleRepository;
import ru.kata.spring.boot_security.bootstrap.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class AdminController {

    private final UserService userService;
    private final RoleRepository roleRepository;

    @Autowired
    public AdminController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("admin/hello")
    public String printWelcome(Model model, Principal principal) {
        List<String> messages = new ArrayList<>();
        messages.add("Hello, " + principal.getName() + "! welcome to the Users world!");
        messages.add("Click the link bellow (:");
        model.addAttribute("messages", messages);
        return "hello";
    }

    @GetMapping(value = "admin/index")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String index(Model model) {
        model.addAttribute("users", userService.allUsers());
        return "index";
    }

    @GetMapping(value = "admin/show")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Optional<User> showOne(int id) {
        return Optional.ofNullable(userService.getById(id));
    }

    @GetMapping(value = "admin/new")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String newPerson (@ModelAttribute("user") User user, Model model){
        model.addAttribute("listRoles", roleRepository.findAll());
        return "new";
    }

    @PostMapping (value = "index")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String create(@ModelAttribute("user") User user,
                         @RequestParam("listRoles") ArrayList<Integer> roles) {
        user.setRoles(roleRepository.findAllById(roles));
        userService.save(user);
        return "redirect:/admin/index";
    }

    @GetMapping(value = "admin/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("user", userService.getById(id));
        model.addAttribute("listRoles", roleRepository.findAll());
        return "edit";
    }

    @PatchMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") int id,
    @RequestParam("listRoles") ArrayList<Integer>roles){
        user.setRoles(roleRepository.findAllById(roles));
        userService.edit(id, user);
        return "redirect:/admin/index";
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@PathVariable("id") int id){
        userService.deleteById(id);
        return "redirect:/admin/index";
    }
}
