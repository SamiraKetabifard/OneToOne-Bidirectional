package com.example.relational_onetoone.controller;

import com.example.relational_onetoone.model.Passport;
import com.example.relational_onetoone.service.PassportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pass")
public class PassportController {

    @Autowired
    private PassportService passportService;

    @PostMapping("/add")
    public String savePassport(@RequestBody Passport passport){
        passportService.savePassport(passport);
        return "Passport num saved";
    }
    @DeleteMapping("/delete/{id}")
    public String deletePassport(@PathVariable long id) {
        passportService.deletePassportById(id);
        return "Passport deleted successfully";
    }
}
