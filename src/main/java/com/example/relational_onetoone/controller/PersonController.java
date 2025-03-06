package com.example.relational_onetoone.controller;

import com.example.relational_onetoone.model.Person;
import com.example.relational_onetoone.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping("/add")
    public String addPerson(@RequestBody Person person) {
        personService.save(person);
        return "person added successfully";
    }
    @GetMapping("/getall")
    public Page<Person> getAllPersons(@RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
                                      @RequestParam("sortField") String sortField,
                                      @RequestParam("sortDir") String sortDir,
                                      @RequestParam(value = "pageSize", defaultValue = "5") int pageSize) {
        return personService.getAll(pageNo, pageSize, sortField, sortDir);
    }

    @GetMapping("/get/{id}")
    public Person getPerson(@PathVariable long id) {
        return personService.findById(id);
    }
    @DeleteMapping("/delete/{id}")
    public String deletePerson(@PathVariable long id) {
        personService.deleteById(id);
        return "Deleted Person";
    }
    @PutMapping("update/{id}")
    public String updatePerson(@PathVariable long id, @RequestBody Person person) {
        Person UpdatedProduct = personService.updatePerson(person, id);
        if (UpdatedProduct != null) {
            return "person updated";
        } else {
            return "person not found";
        }
    }
}
