package com.example.relational_onetoone.repository;

import com.example.relational_onetoone.model.Passport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassportRepository extends JpaRepository<Passport, Long> {
    Long id(Long id);
}
