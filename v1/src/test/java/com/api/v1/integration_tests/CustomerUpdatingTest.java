package com.api.v1.integration_tests;

import com.api.v1.people.dtos.Address;
import com.api.v1.people.enums.Gender;
import com.api.v1.people.requests.PersonRegistrationDto;
import com.api.v1.people.requests.PersonUpdatingDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.util.UUID;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerUpdatingTest {

    @Autowired
    WebTestClient webTestClient;

    PersonUpdatingDto customerDto  = new PersonUpdatingDto(
            "Leonard",
            "Campbell",
            "Smith",
            LocalDate.of(2000,12,12),
            "leosamith@leosamith.com",
            Gender.MALE,
            new Address(
                    "Downtown",
                    "LA",
                    "90012"
            )
    );

    @Test
    @Order(1)
    void shouldReturnSuccessWhenCustomerIsFound() {
        String id = "";
        webTestClient
                .patch()
                .uri("api/v1/customers")
                .bodyValue(customerDto)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    @Test
    @Order(2)
    void shouldReturnNotFoundWhenCustomerWasNotFound() {
        String randomId = UUID.randomUUID().toString();
        webTestClient
                .patch()
                .uri("api/v1/customers/%s".formatted(randomId))
                .bodyValue(customerDto)
                .exchange()
                .expectStatus()
                .is4xxClientError();
    }
}
