package com.example.relational_onetoone.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;


@Entity
public class Passport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    @Column(unique=true)
    private String passportNumber;

    //BiDirectional
    @OneToOne(mappedBy = "passport",orphanRemoval = true)
    @JsonIgnore
    private Person person;

    public Passport() {}
    public Passport(String passportNumber, Person person) {
        this.passportNumber = passportNumber;
        this.person = person;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
