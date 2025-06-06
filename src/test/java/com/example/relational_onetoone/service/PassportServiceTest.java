package com.example.relational_onetoone.service;

import com.example.relational_onetoone.model.Passport;
import com.example.relational_onetoone.repository.PassportRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PassportServiceTest {

    @Mock
    private PassportRepository passportRepository;

    @InjectMocks
    private PassportServiceImpl passportService;

    @Test
    void savePassport_ValidPassport_ShouldCallRepositorySave() {
        // Given
        Passport passport = new Passport();
        passport.setPassportNumber("P123456789");
        // When
        passportService.savePassport(passport);

        // Then
        verify(passportRepository, times(1)).save(passport);
    }

    @Test
    void deletePassportById_ExistingId_ShouldCallRepositoryDelete() {
        // Given
        doNothing().when(passportRepository).deleteById(1L);

        // When
        passportService.deletePassportById(1L);

        // Then
        verify(passportRepository, times(1)).deleteById(1L);
    }
}