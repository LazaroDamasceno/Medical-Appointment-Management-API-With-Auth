package com.api.v1;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;
import java.util.UUID;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MedicalSlotRegistrationIntegrationTest {

    @Autowired
    WebTestClient webTestClient;

    @Order(1)
    @Test
    void testSuccessfulRegistration() {
        String doctorId = "";
        LocalDateTime availableAt = null;
        webTestClient
                .post()
                .uri("api/v1/medical-slots/%s/%s".formatted(doctorId, availableAt))
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    @Order(2)
    @Test
    void testUnsuccessfulRegistration_DoctorNotFound() {
        String doctorId = UUID.randomUUID().toString();
        LocalDateTime availableAt = null;
        webTestClient
                .post()
                .uri("api/v1/medical-slots/%s/%s".formatted(doctorId, availableAt))
                .exchange()
                .expectStatus()
                .is5xxServerError();
    }

    @Order(3)
    @Test
    void testUnsuccessfulRegistration_DuplicatedBookingDateTime() {
        String doctorId = "";
        LocalDateTime availableAt = null;
        webTestClient
                .post()
                .uri("api/v1/medical-slots/%s/%s".formatted(doctorId, availableAt))
                .exchange()
                .expectStatus()
                .is5xxServerError();
    }
}
