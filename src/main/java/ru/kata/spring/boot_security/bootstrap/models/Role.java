package ru.kata.spring.boot_security.bootstrap.models;

import javax.persistence.*;

@Entity
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    @Override
    public String toString() {
        return rolename;
    }
}
