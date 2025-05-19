package com.api.v1.integration_tests;

import com.api.v1.people.dtos.Address;
import com.api.v1.people.enums.Gender;
import com.api.v1.people.requests.PersonRegistrationDTO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerRegistrationTest {

    @Autowired
    WebTestClient webTestClient;

    PersonRegistrationDTO customerDTO  = new PersonRegistrationDTO(
            "Leonard",
            "",
            "Smith",
            "1234567890",
            LocalDate.of(2000,12,12),
            "leosamith@mail.com",
            Gender.MALE,
            new Address(
                    "Downtown",
                    "LA",
                    "90012"
            )
    );

    @Test
    @Order(1)
    void shouldReturnSuccessWhenCustomerIsRegistered() {
        webTestClient.post()
                .uri("/api/v1/customers")
                .bodyValue(customerDTO)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    @Test
    @Order(2)
    void shouldReturnConflictWhenSinIsDuplicated() {
        webTestClient.post()
                .uri("/api/v1/customers")
                .bodyValue(customerDTO)
                .exchange()
                .expectStatus()
                .is4xxClientError();
    }

    PersonRegistrationDTO duplicateEmailDTO = new PersonRegistrationDTO(
            "Leonard",
            "",
            "Smith",
            "0987654321",
            LocalDate.of(2000,12,12),
            "leosamith@mail.com",
            Gender.MALE,
            new Address(
                    "Downtown",
                    "LA",
                    "90012"
            )
    );

    @Test
    @Order(3)
    void shouldReturnConflictWhenEmailIsDuplicated() {
        webTestClient.post()
                .uri("/api/v1/customers")
                .bodyValue(duplicateEmailDTO)
                .exchange()
                .expectStatus()
                .is4xxClientError();
    }
}
