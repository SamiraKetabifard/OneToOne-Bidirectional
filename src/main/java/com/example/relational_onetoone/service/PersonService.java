package com.example.relational_onetoone.service;

import com.example.relational_onetoone.model.Person;
import org.springframework.data.domain.Page;

public interface PersonService {

    void save(Person person);
    Person findById(long id);
    Page<Person> getAll(int pageNo, int pageSize, String sortField, String sortDirection);
    void deleteById(long id);
    Person updatePerson(Person person,long id);

}
