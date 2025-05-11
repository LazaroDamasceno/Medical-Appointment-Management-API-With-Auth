package com.api.v1.medical_appointments;

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
public class MedicalAppointmentRegistrationIntegrationTest {

    @Autowired
    WebTestClient webTestClient;

    @Order(1)
    @Test
    void testSuccessfulRegistration() {
        String customerId = "";
        String doctorLicenseNumber = "";
        LocalDateTime bookedAt = LocalDateTime.of(2025,12,12,12,30,30);
        webTestClient
                .post()
                .uri("api/v1/medical-appointments/%s/%s/%s".formatted(customerId, doctorLicenseNumber, bookedAt))
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    @Order(2)
    @Test
    void testUnsuccessfulRegistration_CustomerNotFound() {
        String customerId = UUID.randomUUID().toString();
        String doctorLicenseNumber = UUID.randomUUID().toString();
        LocalDateTime bookedAt = LocalDateTime.now();
        webTestClient
                .post()
                .uri("api/v1/medical-appointments/%s/%s/%s".formatted(customerId, doctorLicenseNumber, bookedAt))
                .exchange()
                .expectStatus()
                .is4xxClientError();
    }

    @Order(3)
    @Test
    void testUnsuccessfulRegistration_DoctorNotFound() {
        String customerId = "";
        String doctorLicenseNumber = UUID.randomUUID().toString();
        LocalDateTime bookedAt = LocalDateTime.now();
        webTestClient
                .post()
                .uri("api/v1/medical-appointments/%s/%s/%s".formatted(customerId, doctorLicenseNumber, bookedAt))
                .exchange()
                .expectStatus()
                .is4xxClientError();
    }

    @Order(4)
    @Test
    void testUnsuccessfulRegistration_DuplicatedBookingDateTime() {
        String customerId = "";
        String doctorLicenseNumber = "";
        LocalDateTime bookedAt = LocalDateTime.of(2025,12,12,12,30,30);
        webTestClient
                .post()
                .uri("api/v1/medical-appointments/%s/%s/%s".formatted(customerId, doctorLicenseNumber, bookedAt))
                .exchange()
                .expectStatus()
                .is4xxClientError();
    }

    @Order(5)
    @Test
    void testUnsuccessfulRegistration_NonAssociatedDoctor() {
        String customerId = "";
        String doctorLicenseNumber = "";
        LocalDateTime bookedAt = LocalDateTime.now();
        webTestClient
                .post()
                .uri("api/v1/medical-appointments/%s/%s/%s".formatted(customerId, doctorLicenseNumber, bookedAt))
                .exchange()
                .expectStatus()
                .is4xxClientError();
    }

    @Order(6)
    @Test
    void testUnsuccessfulRegistration_SelfAppointment() {
        String customerId = "";
        String doctorLicenseNumber = "";
        LocalDateTime bookedAt = LocalDateTime.now();
        webTestClient
                .post()
                .uri("api/v1/medical-appointments/%s/%s/%s".formatted(customerId, doctorLicenseNumber, bookedAt))
                .exchange()
                .expectStatus()
                .is4xxClientError();
    }

    @Order(7)
    @Test
    void testUnsuccessfulRegistration_NonExistentBookingDateTime() {
        String customerId = "";
        String doctorLicenseNumber = "";
        LocalDateTime bookedAt = LocalDateTime.now();
        webTestClient
                .post()
                .uri("api/v1/medical-appointments/%s/%s/%s".formatted(customerId, doctorLicenseNumber, bookedAt))
                .exchange()
                .expectStatus()
                .is4xxClientError();
    }

}
