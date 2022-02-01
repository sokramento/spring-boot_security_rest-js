package ru.kata.spring.boot_security.bootstrap.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.bootstrap.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("login")
    public String getLogin(){
        return "login";
    }

    @GetMapping("user/hello")
    public String printWelcome(Model model, Principal principal) {
        List<String> messages = new ArrayList<>();
        messages.add("Hello, " + principal.getName() + "! welcome to the Users world!");
        messages.add("Click the link bellow (:");
        model.addAttribute("messages", messages);
        return "hello";
    }

    @GetMapping("/user/info")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public String pageForUser (Model model, Principal principal) {
        model.addAttribute("user",userService.findByUsername(principal.getName()));
        return "user";
    }
}