package ru.kata.spring.boot_security.bootstrap.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name="users")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "username")
    private String username;
    @Column(name = "surname")
    private String surname;
    @Column(name = "password")
    private String password;
    @Column(name = "age")
    private int age;
    @Column(name = "Email")
    private String email;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User(String username, String surname,
                String password, int age, String email,
                Set<Role> roles) {
        this.username = username;
        this.surname = surname;
        this.password = password;
        this.age = age;
        this.email = email;
        this.roles = roles;
    }

    public User() {

    }

}
