package com.example.relational_onetoone.service;

import com.example.relational_onetoone.model.Person;
import com.example.relational_onetoone.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public void save(Person person) {
        personRepository.save(person);
    }

    @Override
    public Person findById(long id) {
        return personRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Person> getAll(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.personRepository.findAll(pageable);
    }

    @Override
    public void deleteById(long id) {
        personRepository.deleteById(id);
    }

    @Override
    public Person updatePerson(Person person, long id) {
        Person existingPerson = personRepository.findById(id).orElse(null);
        if (existingPerson != null) {
            existingPerson.setFullName(person.getFullName());
            existingPerson.setPassport(person.getPassport());
            personRepository.save(existingPerson);
            return existingPerson;
        } else {
            throw new RuntimeException("Person not found with id: " + id);
        }
    }
}
