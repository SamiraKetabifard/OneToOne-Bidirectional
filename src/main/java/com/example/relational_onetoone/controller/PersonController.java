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
   /* {
        "fullName": "Ali Rezaei",
            "passport": {
        "passportNumber": "P123456789"
    }
    }*/
    @PostMapping("/add")
    public String addPerson(@RequestBody Person person) {
        personService.save(person);
        return "person added successfully";
    }
    /*http://localhost:8080/person/getall?pageNo=1&pageSize=5&sortField=fullName&sortDir=asc*/
    @GetMapping("/getall")
    public Page<Person> getAllPersons(@RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
                                      @RequestParam("sortField") String sortField,
                                      @RequestParam("sortDir") String sortDir,
                                      @RequestParam(value = "pageSize", defaultValue = "5") int pageSize) {
        return personService.getAll(pageNo, pageSize, sortField, sortDir);
    }
    /*http://localhost:8080/person/get/14*/
    @GetMapping("/get/{id}")
    public Person getPerson(@PathVariable long id) {
        return personService.findById(id);
    }
    /*http://localhost:8080/person/delete/14*/
    @DeleteMapping("/delete/{id}")
    public String deletePerson(@PathVariable long id) {
        personService.deleteById(id);
        return "Deleted Person";
    }
    /*http://localhost:8080/person/update/1
    {
  "fullName": "Ali Rezaei",
  "passport": {
    "passportNumber": "P987654321"
  }
}
*/
    @PutMapping("update/{id}")
    public String updatePerson(@PathVariable long id, @RequestBody Person person) {
        Person UpdatedPerson = personService.updatePerson(person, id);
        if (UpdatedPerson != null) {
            return "person updated";
        } else {
            return "person not found";
        }
    }
}
