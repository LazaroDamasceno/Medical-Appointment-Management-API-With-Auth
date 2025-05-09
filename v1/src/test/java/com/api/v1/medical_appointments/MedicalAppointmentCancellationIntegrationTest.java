package com.api.v1.medical_appointments;

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
public class MedicalAppointmentCancellationIntegrationTest {

    @Autowired
    WebTestClient webTestClient;

    @Order(1)
    @Test
    void testSuccessfulCancellation() {
        String customerId = "";
        String slotId = "";
        webTestClient
                .patch()
                .uri("api/v1/medical-appointments/%s/%s/cancellation".formatted(customerId, slotId))
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    @Order(2)
    @Test
    void testUnsuccessfulCancellation_CustomerNotFound() {
        String customerId = UUID.randomUUID().toString();
        String slotId = UUID.randomUUID().toString();
        webTestClient
                .patch()
                .uri("api/v1/medical-appointments/%s/%s/cancellation".formatted(customerId, slotId))
                .exchange()
                .expectStatus()
                .is4xxClientError();
    }

    @Order(3)
    @Test
    void testUnsuccessfulCancellation_MedicalAppointmentNotFound() {
        String customerId = "";
        String slotId = UUID.randomUUID().toString();
        webTestClient
                .patch()
                .uri("api/v1/medical-appointments/%s/%s/cancellation".formatted(customerId, slotId))
                .exchange()
                .expectStatus()
                .is4xxClientError();
    }

    @Order(4)
    @Test
    void testUnsuccessfulRegistration_NonAssociatedCustomer() {
        String customerId = "";
        String slotId = "";
        webTestClient
                .patch()
                .uri("api/v1/medical-appointments/%s/%s/cancellation".formatted(customerId, slotId))
                .exchange()
                .expectStatus()
                .is4xxClientError();
    }

    @Order(5)
    @Test
    void testUnsuccessfulRegistration_CanceledAppointment() {
        String customerId = "";
        String slotId = "";
        webTestClient
                .patch()
                .uri("api/v1/medical-appointments/%s/%s/cancellation".formatted(customerId, slotId))
                .exchange()
                .expectStatus()
                .is4xxClientError();
    }

    @Order(6)
    @Test
    void testUnsuccessfulRegistration_CompletedAppointment() {
        String customerId = "";
        String slotId = "";
        webTestClient
                .patch()
                .uri("api/v1/medical-appointments/%s/%s/cancellation".formatted(customerId, slotId))
                .exchange()
                .expectStatus()
                .is4xxClientError();
    }

}
