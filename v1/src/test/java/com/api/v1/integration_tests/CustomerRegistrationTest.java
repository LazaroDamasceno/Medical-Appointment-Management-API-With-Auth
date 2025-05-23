package com.api.v1.integration_tests;

import com.api.v1.people.dtos.Address;
import com.api.v1.people.enums.Gender;
import com.api.v1.people.requests.PersonRegistrationDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CustomerRegistrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    PersonRegistrationDTO customerDTO  = new PersonRegistrationDTO(
            "Leonard",
            "",
            "Smith",
            "1234567890",
            LocalDate.of(2000,12,12),
            "leosmith@mail.com",
            Gender.MALE,
            new Address(
                    "Downtown",
                    "LA",
                    "90012"
            ),
            "1234567890"
    );

    PersonRegistrationDTO duplicateEmailDTO = new PersonRegistrationDTO(
            "Leonard",
            "",
            "Smith",
            "0987654321",
            LocalDate.of(2000,12,12),
            "leosmith@mail.com",
            Gender.MALE,
            new Address(
                    "Downtown",
                    "LA",
                    "90012"
            ),
            "1234567890"
    );

    @Test
    @Order(1)
    void shouldReturnCreatedWhenCustomerIsRegistered() throws Exception {
        mockMvc.perform(post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(2)
    void shouldReturnConflictWhenSinIsDuplicated() throws Exception {
        mockMvc.perform(post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDTO)))
                .andExpect(status().isConflict());
    }

    @Test
    @Order(3)
    void shouldReturnConflictWhenEmailIsDuplicated() throws Exception {
        mockMvc.perform(post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(duplicateEmailDTO)))
                .andExpect(status().isConflict());
    }
}