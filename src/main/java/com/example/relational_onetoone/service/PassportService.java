package com.example.relational_onetoone.service;
import com.example.relational_onetoone.model.Passport;

public interface PassportService {

    void savePassport(Passport passport);
    void deletePassportById(long id);
}
