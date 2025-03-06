package com.example.relational_onetoone.service;

import com.example.relational_onetoone.model.Passport;
import com.example.relational_onetoone.repository.PassportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PassportServiceImpl implements PassportService {

    @Autowired
    private PassportRepository passportRepository;

    @Override
    public void savePassport(Passport passport) {
        passportRepository.save(passport);
    }
    @Override
    public void deletePassportById(long id) {
        passportRepository.deleteById(id);
    }
}
