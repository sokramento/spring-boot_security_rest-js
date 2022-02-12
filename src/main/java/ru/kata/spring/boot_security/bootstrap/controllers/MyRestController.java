package ru.kata.spring.boot_security.bootstrap.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.bootstrap.models.User;
import ru.kata.spring.boot_security.bootstrap.repositories.RoleRepository;
import ru.kata.spring.boot_security.bootstrap.service.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api")
public class MyRestController {

    private final UserService userService;

    @Autowired
    public MyRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.allUsers());
    }

    @GetMapping("users/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<User> getUser(@PathVariable(value = "id") int id){
        return ResponseEntity.ok(userService.getById(id));
    }

    @PostMapping("users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void create(@RequestBody User user){ userService.save(user);
    }

    @PatchMapping("users/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void update(@RequestBody User user,
                       @PathVariable(value = "id") int id){
        userService.edit(id, user);
    }

    @DeleteMapping("users/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(@PathVariable(value = "id") int id){
        userService.deleteById(id);
    }

}
