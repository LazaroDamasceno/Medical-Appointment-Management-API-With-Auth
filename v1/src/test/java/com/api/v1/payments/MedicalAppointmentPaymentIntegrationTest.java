package com.api.v1.payments;

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
public class MedicalAppointmentPaymentIntegrationTest {

    @Autowired
    WebTestClient webTestClient;

    @Order(1)
    @Test
    void testSuccessful() {
        String appointmentId = "";
        webTestClient
                .post()
                .uri("api/v1/payments/%s".formatted(appointmentId))
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    @Order(2)
    @Test
    void testUnsuccessful_MedicalAppointmentNotFound() {
        String appointmentId = UUID.randomUUID().toString();
        webTestClient
                .post()
                .uri("api/v1/payments/%s".formatted(appointmentId))
                .exchange()
                .expectStatus()
                .is4xxClientError();
    }

    @Order(3)
    @Test
    void testUnsuccessful_CanceledMedicalAppointment() {
        String appointmentId = "";
        webTestClient
                .post()
                .uri("api/v1/payments/%s".formatted(appointmentId))
                .exchange()
                .expectStatus()
                .is4xxClientError();
    }

    @Order(4)
    @Test
    void testUnsuccessful_CompletedMedicalAppointment() {
        String appointmentId = "";
        webTestClient
                .post()
                .uri("api/v1/payments/%s".formatted(appointmentId))
                .exchange()
                .expectStatus()
                .is4xxClientError();
    }

}
