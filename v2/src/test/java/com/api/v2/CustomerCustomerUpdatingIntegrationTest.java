package com.api.v2;

import com.api.v2.common.States;
import com.api.v2.people.dtos.Address;
import com.api.v2.people.enums.Gender;
import com.api.v2.people.requests.PersonRegistrationDto;
import com.api.v2.people.requests.PersonUpdatingDto;
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
public class CustomerCustomerUpdatingIntegrationTest {

    @Autowired
    WebTestClient webTestClient;

    PersonUpdatingDto registrationDto = new PersonUpdatingDto(
            "Leonard",
            "Santos",
            "Smith",
            LocalDate.of(2000,12,12),
            "1234567890",
            Gender.CIS_MALE,
            "leosmith@leosmith.com",
            new Address(
                    "Downtown",
                    "Los Angeles",
                    States.CA,
                    "90012"
            )
    );

    @Test
    @Order(1)
    void testSuccessfulUpdating() {
        String id = "";
        webTestClient
                .patch()
                .uri("api/v2/customers/%s".formatted(id))
                .bodyValue(registrationDto)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    @Test
    @Order(2)
    void testUnsuccessfulUpdating_Customer_Not_Found() {
        String id = UUID.randomUUID().toString();
        webTestClient
                .patch()
                .uri("api/v2/customers/%s".formatted(id))
                .bodyValue(registrationDto)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

}
