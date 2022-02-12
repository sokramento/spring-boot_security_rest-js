package ru.kata.spring.boot_security.bootstrap.models;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="users")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "username")
    @NotNull
    private String username;
    @Column(name = "surname")
    @NotNull
    private String surname;
    @Column(name = "password")
    @NotNull
    private String password;
    @Column(name = "age")
    @NotNull
    private int age;
    @Column(name = "Email")
    @NotNull
    private String email;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    public User(String username, String surname,
                String password, int age, String email,
                Collection<Role> roles) {
        this.username = username;
        this.surname = surname;
        this.password = password;
        this.age = age;
        this.email = email;
        this.roles = roles;
    }

    public User() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }
}
