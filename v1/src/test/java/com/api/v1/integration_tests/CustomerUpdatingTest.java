package com.api.v1.integration_tests;

import com.api.v1.people.dtos.Address;
import com.api.v1.people.enums.Gender;
import com.api.v1.people.PersonUpdateDTO;
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
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CustomerUpdatingTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    PersonUpdateDTO customerDTO  = new PersonUpdateDTO(
            "Leonard",
            "Campbell",
            "Smith",
            LocalDate.of(2000,12,12),
            "leosmith@leosmith.com",
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
    void shouldReturnNoContentWhenCustomerSuccessful() throws Exception {
        String customerId = "";
        mockMvc.perform(patch("/api/v1/customers/%s".formatted(customerId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDTO)))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(2)
    void shouldReturnNotFoundWhenCustomerWasNotFound() throws Exception {
        String randomId = UUID.randomUUID().toString();
        mockMvc.perform(patch("/api/v1/customers/%s".formatted(randomId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDTO)))
                .andExpect(status().isNotFound());
    }
}