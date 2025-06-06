package com.example.relational_onetoone.repository;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.example.relational_onetoone.model.Passport;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PassportRepositoryTest {

    @Autowired
    private PassportRepository passportRepository;

    @Test
    void savePassport_WithValidData_ShouldPersist() {
        Passport passport = new Passport();
        passport.setPassportNumber("P12345678");
        Passport savedPassport = passportRepository.save(passport);
        assertNotNull(savedPassport.getId());
        assertEquals("P12345678", savedPassport.getPassportNumber());
        assertTrue(passportRepository.existsById(savedPassport.getId()));
    }
    @Test
    void findById_WithExistingId_ShouldReturnPassport() {
        // Given
        Passport passport = new Passport();
        passport.setPassportNumber("P12345678");
        Passport savedPassport = passportRepository.save(passport);
        Passport foundPassport = passportRepository.findById(savedPassport.getId())
                .orElse(null);
        assertNotNull(foundPassport);
        assertEquals(savedPassport.getId(), foundPassport.getId());
        assertEquals("P12345678", foundPassport.getPassportNumber());
    }
    @Test
    void deleteById_WithExistingId_ShouldRemovePassport() {
        Passport passport = new Passport();
        passport.setPassportNumber("P12345678");
        Passport savedPassport = passportRepository.save(passport);
        passportRepository.deleteById(savedPassport.getId());
        assertFalse(passportRepository.existsById(savedPassport.getId()));
    }
    @Test
    void findById_WithNonExistingId_ShouldReturnEmpty() {
        var result = passportRepository.findById(999L);
        assertTrue(result.isEmpty());
    }
}
