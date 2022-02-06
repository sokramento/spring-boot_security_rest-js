package ru.kata.spring.boot_security.bootstrap.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "rolename")
    private String rolename;

    public Role(int id, String rolename) {
        this.id = id;
        this.rolename = rolename;
    }

    public Role() {
    }

    public Role(String rolename) {
        this.rolename = rolename;
    }

    @Override
    public String toString() {
        return rolename;
    }
}
