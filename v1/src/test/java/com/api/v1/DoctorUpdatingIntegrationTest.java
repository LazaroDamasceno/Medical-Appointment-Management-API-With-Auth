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
import java.util.UUID;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DoctorUpdatingIntegrationTest {

    @Autowired
    WebTestClient webTestClient;

    PersonUpdatingDto updatingDto = new PersonUpdatingDto(
                    "Christian",
                    "Smith",
                    "Sewer",
                    LocalDate.of(2000,12,12),
                    "1234567890",
                    Gender.CIS_MALE,
                    "chrissewer@chrissewer.com",
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
                .uri("api/v1/doctors/%s".formatted(id))
                .bodyValue(updatingDto)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    @Test
    @Order(2)
    void testUnsuccessfulUpdating_DoctorNotFound() {
        String id = UUID.randomUUID().toString();
        webTestClient
                .patch()
                .uri("api/v1/doctors/%s".formatted(id))
                .bodyValue(updatingDto)
                .exchange()
                .expectStatus()
                .is5xxServerError();
    }

}
