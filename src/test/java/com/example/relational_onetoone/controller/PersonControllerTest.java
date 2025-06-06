package com.example.relational_onetoone.controller;

import com.example.relational_onetoone.model.Passport;
import com.example.relational_onetoone.model.Person;
import com.example.relational_onetoone.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(PersonController.class)
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PersonService personService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void addPerson_ValidInput_ShouldReturnSuccessMessage() throws Exception {
        // Given
        Person person = new Person("Ali Rezaei", new Passport("P123456789"));
        String requestBody = objectMapper.writeValueAsString(person);
        // When/Then
        mockMvc.perform(post("/person/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string("person added successfully"));

        verify(personService, times(1)).save(any(Person.class));
    }

    @Test
    void getPerson_ExistingId_ShouldReturnPerson() throws Exception {
        // Given
        Person person = new Person();
        person.setId(1L);
        person.setFullName("Ali Rezaei");

        Passport passport = new Passport();
        passport.setId(1L);
        passport.setPassportNumber("P123456789");

        person.setPassport(passport);

        when(personService.findById(1L)).thenReturn(person);

        // When/Then
        mockMvc.perform(get("/person/get/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.fullName").value("Ali Rezaei"))
                .andExpect(jsonPath("$.passport.passportNumber").value("P123456789"));
    }

    @Test
    void getAllPersons_ValidParameters_ShouldReturnPage() throws Exception {
        // Given
        Person person1 = new Person();
        person1.setId(1L);
        person1.setFullName("Ali Rezaei");

        Passport passport1 = new Passport();
        passport1.setId(1L);
        passport1.setPassportNumber("P123456789");
        person1.setPassport(passport1);

        Page<Person> page = new PageImpl<>(List.of(person1));
        when(personService.getAll(1, 5, "fullName", "asc")).thenReturn(page);

        // When/Then
        mockMvc.perform(get("/person/getall")
                        .param("pageNo", "1")
                        .param("pageSize", "5")
                        .param("sortField", "fullName")
                        .param("sortDir", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].fullName").value("Ali Rezaei"));
    }

    @Test
    void updatePerson_ValidInput_ShouldReturnSuccessMessage() throws Exception {
        // Given
        Person updatedPerson = new Person();
        updatedPerson.setFullName("Ali Rezaei Updated");

        Passport passport = new Passport();
        passport.setPassportNumber("P987654321");
        updatedPerson.setPassport(passport);

        when(personService.updatePerson(any(Person.class), eq(1L))).thenReturn(updatedPerson);

        // When/Then
        mockMvc.perform(put("/person/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPerson)))
                .andExpect(status().isOk())
                .andExpect(content().string("person updated"));
    }

    @Test
    void deletePerson_ExistingId_ShouldReturnSuccessMessage() throws Exception {
        // Given
        doNothing().when(personService).deleteById(1L);

        // When/Then
        mockMvc.perform(delete("/person/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted Person"));

        verify(personService, times(1)).deleteById(1L);
    }

    @Test
    void getPerson_NonExistingId_ShouldReturnNotFound() throws Exception {
        // Given
        when(personService.findById(999L)).thenReturn(null);

        // When/Then
        mockMvc.perform(get("/person/get/999"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }
}