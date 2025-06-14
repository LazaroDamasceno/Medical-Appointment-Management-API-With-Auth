package com.api.v1.integration_tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class MedicalSlotRegistrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
    void shouldReturnCreatedWhenSuccessful() throws Exception {
        String medicalLicenseNumber = "";
        LocalDateTime availableAt = LocalDateTime.of(2025,12,12,14,30,30);
        mockMvc.perform(
                post("/api/v1/medical-slots/%s/%s".formatted(medicalLicenseNumber, availableAt))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());
    }

    @Test
    @Order(2)
    void shouldReturnConflictWhenBookingDateTimeIsDuplicated() throws Exception {
        String medicalLicenseNumber = "";
        LocalDateTime availableAt = LocalDateTime.of(2025,12,12,14,30,30);
        mockMvc.perform(
                post("/api/v1/medical-slots/%s/%s".formatted(medicalLicenseNumber, availableAt))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isConflict());
    }

    @Test
    @Order(3)
    void shouldReturnNotFoundWhenDoctorWasNotFound() throws Exception {
        String medicalLicenseNumber = UUID
                .randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 10);
        LocalDateTime availableAt = LocalDateTime.of(2025,12,12,14,30,30);
        mockMvc.perform(
                post("/api/v1/medical-slots/%s/%s".formatted(medicalLicenseNumber, availableAt))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }
}
