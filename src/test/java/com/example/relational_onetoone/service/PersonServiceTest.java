package com.example.relational_onetoone.service;

import com.example.relational_onetoone.model.Passport;
import com.example.relational_onetoone.model.Person;
import com.example.relational_onetoone.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonServiceImpl personService;

    @Test
    void save_ValidPerson_ShouldCallRepositorySave() {
        // Given
        Person person = new Person();
        person.setFullName("Ali Rezaei");

        Passport passport = new Passport();
        passport.setPassportNumber("P123456789");
        person.setPassport(passport);

        // When
        personService.save(person);

        // Then
        verify(personRepository, times(1)).save(person);
    }

    @Test
    void findById_ExistingId_ShouldReturnPerson() {
        // Given
        Person expectedPerson = new Person();
        expectedPerson.setId(1L);
        expectedPerson.setFullName("Ali Rezaei");

        when(personRepository.findById(1L)).thenReturn(Optional.of(expectedPerson));

        // When
        Person result = personService.findById(1L);

        // Then
        assertEquals(expectedPerson, result);
        verify(personRepository, times(1)).findById(1L);
    }

    @Test
    void getAll_ValidParameters_ShouldReturnPage() {
        // Given
        Pageable pageable = PageRequest.of(0, 5, Sort.by("fullName").ascending());

        Person person = new Person();
        person.setId(1L);
        person.setFullName("Ali Rezaei");

        Page<Person> expectedPage = new PageImpl<>(List.of(person));
        when(personRepository.findAll(pageable)).thenReturn(expectedPage);

        // When
        Page<Person> result = personService.getAll(1, 5, "fullName", "asc");

        // Then
        assertEquals(expectedPage, result);
        verify(personRepository, times(1)).findAll(pageable);
    }

    @Test
    void deleteById_ExistingId_ShouldCallRepositoryDelete() {
        // Given
        doNothing().when(personRepository).deleteById(1L);

        // When
        personService.deleteById(1L);

        // Then
        verify(personRepository, times(1)).deleteById(1L);
    }

    @Test
    void updatePerson_ValidInput_ShouldUpdatePerson() {
        // Given
        Person existingPerson = new Person();
        existingPerson.setId(1L);
        existingPerson.setFullName("Ali Rezaei");

        Passport existingPassport = new Passport();
        existingPassport.setId(1L);
        existingPassport.setPassportNumber("P123456789");
        existingPerson.setPassport(existingPassport);

        Person updatedDetails = new Person();
        updatedDetails.setFullName("Ali Rezaei Updated");

        Passport updatedPassport = new Passport();
        updatedPassport.setPassportNumber("P987654321");
        updatedDetails.setPassport(updatedPassport);

        when(personRepository.findById(1L)).thenReturn(Optional.of(existingPerson));
        when(personRepository.save(any(Person.class))).thenReturn(existingPerson);

        // When
        Person result = personService.updatePerson(updatedDetails, 1L);

        // Then
        assertEquals("Ali Rezaei Updated", result.getFullName());
        assertEquals("P987654321", result.getPassport().getPassportNumber());
        verify(personRepository, times(1)).findById(1L);
        verify(personRepository, times(1)).save(existingPerson);
    }
    @Test
    void findById_NonExistingId_ShouldReturnNull() {
        // Given
        when(personRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        Person result = personService.findById(999L);

        // Then
        assertNull(result);
    }
    @Test
    void updatePerson_NonExistingId_ShouldThrowException() {
        // Given
        when(personRepository.findById(999L)).thenReturn(Optional.empty());
        Person updatedDetails = new Person();

        // When/Then
        assertThrows(RuntimeException.class, () ->
                personService.updatePerson(updatedDetails, 999L));
    }
}
