package com.example.relational_onetoone.model;

import jakarta.persistence.*;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String fullName;

    //Owner of relation
    //BiDirectional
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "passport_id", unique = true, nullable = false)
    private Passport passport;

    public Person() {}

    public Person(String fullName, Passport passport) {
        this.fullName = fullName;
        this.passport = passport;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Passport getPassport() {
        return passport;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }
}
