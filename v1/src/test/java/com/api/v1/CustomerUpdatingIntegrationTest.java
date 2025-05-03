package com.api.v1;

import com.api.v1.common.States;
import com.api.v1.people.dtos.Address;
import com.api.v1.people.enums.Gender;
import com.api.v1.people.requests.PersonUpdatingDto;
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
public class CustomerUpdatingIntegrationTest {

    @Autowired
    WebTestClient webTestClient;

    PersonUpdatingDto updatingDto = new PersonUpdatingDto(
            "Leonard",
            "Moore",
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
        String id = "2bef5d08-d5ca-47a8-b467-3da1a18a894f";
        webTestClient
                .patch()
                .uri("api/v1/customers/%s".formatted(id))
                .bodyValue(updatingDto)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

}
