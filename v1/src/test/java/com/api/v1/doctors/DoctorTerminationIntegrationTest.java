package com.api.v1.doctors;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.UUID;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DoctorTerminationIntegrationTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    @Order(1)
    void testSuccessfulTermination() {
        String id  = "";
        webTestClient
                .patch()
                .uri("api/v1/doctors/%s/termination".formatted(id ))
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    @Test
    @Order(2)
    void testUnsuccessfulTermination_DoctorNotFound() {
        String id  = UUID.randomUUID().toString();
        webTestClient
                .patch()
                .uri("api/v1/doctors/%s/termination".formatted(id ))
                .exchange()
                .expectStatus()
                .is5xxServerError();
    }

    @Test
    @Order(3)
    void testUnsuccessfulTermination_TerminatedDoctor() {
        String id  = "";
        webTestClient
                .patch()
                .uri("api/v1/doctors/%s/termination".formatted(id ))
                .exchange()
                .expectStatus()
                .is5xxServerError();
    }

}
