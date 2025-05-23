package com.api.v1.integration_tests;

import com.api.v1.people.dtos.Address;
import com.api.v1.people.enums.Gender;
import com.api.v1.people.requests.PersonUpdateDTO;
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

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class DoctorUpdateTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    PersonUpdateDTO personDTO = new PersonUpdateDTO(
            "Willian",
            "Smith",
            "Belfast",
            LocalDate.of(2000,12,12),
            "contect@drwillianbelfast.com",
            Gender.MALE,
            new Address(
                    "Downtown",
                    "LA",
                    "90012"
            ),
            "1234567890"
    );

    @Order(1)
    @Test
    void shouldReturnNoContentWhenSuccessful() throws Exception {
        var doctorId = "";
        mockMvc.perform(patch("/api/v1/doctors/%s".formatted(doctorId))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(personDTO))
        ).andExpect(status().isNoContent());
    }

    @Order(1)
    @Test
    void shouldReturnNotFoundWhenDoctorWasNotFound() throws Exception {
        var doctorId = "";
        mockMvc.perform(patch("/api/v1/doctors/%s".formatted(doctorId))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(personDTO))
        ).andExpect(status().isNotFound());
    }
}
