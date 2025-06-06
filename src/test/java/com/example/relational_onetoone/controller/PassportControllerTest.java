package com.example.relational_onetoone.controller;

import com.example.relational_onetoone.model.Passport;
import com.example.relational_onetoone.service.PassportService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(PassportController.class)
class PassportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PassportService passportService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void savePassport_ValidInput_ShouldReturnSuccessMessage() throws Exception {
        // Given
        Passport passport = new Passport("P123456789");
        String requestBody = objectMapper.writeValueAsString(passport);
        // When/Then
        mockMvc.perform(post("/pass/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string("Passport num saved"));

        verify(passportService, times(1)).savePassport(any(Passport.class));
    }
    @Test
    void deletePassport_ExistingId_ShouldReturnSuccessMessage() throws Exception {
        doNothing().when(passportService).deletePassportById(1L);
        mockMvc.perform(delete("/pass/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Passport deleted successfully"));
        verify(passportService, times(1)).deletePassportById(1L);
    }

}
