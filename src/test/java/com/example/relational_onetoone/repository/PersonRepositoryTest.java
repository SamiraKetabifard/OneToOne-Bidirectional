package com.example.relational_onetoone.repository;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.example.relational_onetoone.model.Passport;
import com.example.relational_onetoone.model.Person;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PassportRepository passportRepository;

    @Test
    void savePerson_WithValidData_ShouldPersist() {
        Passport passport = new Passport();
        passport.setPassportNumber("P12345678");
        Passport savedPassport = passportRepository.save(passport);
        Person person = new Person();
        person.setFullName("Ali Rezaei");
        person.setPassport(savedPassport);
        // When
        Person savedPerson = personRepository.save(person);
        // Then
        assertNotNull(savedPerson.getId());
        assertEquals("Ali Rezaei", savedPerson.getFullName());
        assertEquals(savedPassport.getId(), savedPerson.getPassport().getId());
        assertTrue(personRepository.existsById(savedPerson.getId()));
    }
    @Test
    void findById_WithExistingId_ShouldReturnPerson() {
        // Given
        Passport passport = new Passport();
        passport.setPassportNumber("P12345678");
        Passport savedPassport = passportRepository.save(passport);
        Person person = new Person();
        person.setFullName("Ali Rezaei");
        person.setPassport(savedPassport);
        Person savedPerson = personRepository.save(person);
        // When
        Person foundPerson = personRepository.findById(savedPerson.getId())
                .orElse(null);
        // Then
        assertNotNull(foundPerson);
        assertEquals(savedPerson.getId(), foundPerson.getId());
        assertEquals("Ali Rezaei", foundPerson.getFullName());
        assertEquals(savedPassport.getId(), foundPerson.getPassport().getId());
    }
    @Test
    void findById_WithNonExistingId_ShouldReturnEmpty() {
        // When
        var result = personRepository.findById(999L);
        // Then
        assertTrue(result.isEmpty());
    }
}