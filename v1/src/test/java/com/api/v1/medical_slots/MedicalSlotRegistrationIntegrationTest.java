package com.api.v1.medical_slots;

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
        String doctorLicenseNumber = "";
        LocalDateTime availableAt = LocalDateTime.of(2025,12,12,12,30,30);
        webTestClient
                .post()
                .uri("api/v1/medical-slots/%s/%s".formatted(doctorLicenseNumber, availableAt))
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    @Order(2)
    @Test
    void testUnsuccessfulRegistration_DoctorNotFound() {
        String doctorLicenseNumber = UUID.randomUUID().toString();
        LocalDateTime availableAt = LocalDateTime.now();
        webTestClient
                .post()
                .uri("api/v1/medical-slots/%s/%s".formatted(doctorLicenseNumber, availableAt))
                .exchange()
                .expectStatus()
                .is4xxClientError();
    }

    @Order(3)
    @Test
    void testUnsuccessfulRegistration_DuplicatedBookingDateTime() {
        String doctorLicenseNumber = "";
        LocalDateTime availableAt = LocalDateTime.of(2025,12,12,12,30,30);
        webTestClient
                .post()
                .uri("api/v1/medical-slots/%s/%s".formatted(doctorLicenseNumber, availableAt))
                .exchange()
                .expectStatus()
                .is4xxClientError();
    }
}
