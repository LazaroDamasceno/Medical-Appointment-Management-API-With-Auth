package com.api.v1.integration_tests;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class MedicalSlotCompletionTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    void shouldReturnNoContentWhenSuccessful() throws Exception {
        String medicalLicenseNumber = "";
        String id = "";
        mockMvc.perform(
                patch("/api/v1/medical-slots/%s/%s/completion".formatted(medicalLicenseNumber, id))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());
    }

    @Test
    @Order(2)
    void shouldReturnConflictWhenDoctorWasNotFound() throws Exception {
        String medicalLicenseNumber = UUID
                .randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 10);
        String id = "";
        mockMvc.perform(
                patch("/api/v1/medical-slots/%s/%s/completion".formatted(medicalLicenseNumber, id))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

    @Test
    @Order(3)
    void shouldReturnConflictWhenMedicalSlotWasNotFound() throws Exception {
        String medicalLicenseNumber = "";
        String id = UUID.randomUUID().toString();
        mockMvc.perform(
                patch("/api/v1/medical-slots/%s/%s/completion".formatted(medicalLicenseNumber, id))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

    @Test
    @Order(4)
    void shouldReturnConflictWhenMedicalSlotIsCurrentlyCancelled() throws Exception {
        String medicalLicenseNumber = "";
        String id = "";
        mockMvc.perform(
                patch("/api/v1/medical-slots/%s/%s/completion".formatted(medicalLicenseNumber, id))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isConflict());
    }

    @Test
    @Order(5)
    void shouldReturnConflictWhenMedicalSlotIsCurrentlyCompleted() throws Exception {
        String medicalLicenseNumber = "";
        String id = "";
        mockMvc.perform(
                patch("/api/v1/medical-slots/%s/%s/completion".formatted(medicalLicenseNumber, id))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isConflict());
    }
}
