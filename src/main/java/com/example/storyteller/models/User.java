package com.example.storyteller.models;


import jakarta.persistence.*;
import java.util.*;
@Entity
@Table(name = "users")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_user;
    @Column(unique = true, updatable = false)
    private String email;
    private String nickname;
    private boolean active;
    private String activationCode;
    @Column(length = 1000)
    private String password;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER,
            mappedBy = "user")
    private List<Work> works = new ArrayList<>();
    public void addWorkToUser(Work product) {
        product.setAuthor(this);
        works.add(product);
    }

    public List<Work> getWorks() {
        return works;
    }

    public void setWorks(List<Work> products) {
        this.works = products;
    }

    public Long getId() {
        return id_user;
    }

    public void setId(Long id) {
        this.id_user = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return nickname;
    }

    public void setName(String name) {
        this.nickname = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

}
