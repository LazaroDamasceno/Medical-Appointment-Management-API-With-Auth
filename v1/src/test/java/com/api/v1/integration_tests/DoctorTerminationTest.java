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

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class DoctorTerminationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
    void shouldReturnNoContentWhenSuccessful() throws Exception {
        String licenseNumber = "";
         mockMvc.perform(patch("/api/v1/doctors/%s/termination".formatted(licenseNumber))
                 .contentType(MediaType.APPLICATION_JSON)
         ).andExpect(status().isNoContent());
    }

    @Test
    @Order(2)
    void shouldReturnConflictWhenDoctorIsCurrentlyTerminated() throws Exception {
        String licenseNumber = "";
        mockMvc.perform(patch("/api/v1/doctors/%s/termination".formatted(licenseNumber))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isConflict());
    }

    @Test
    @Order(3)
    void shouldReturnNotFoundWhenDoctorWasNotFound() throws Exception {
        String licenseNumber = UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 10);
        mockMvc.perform(patch("/api/v1/doctors/%s/termination".formatted(licenseNumber))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }
}
