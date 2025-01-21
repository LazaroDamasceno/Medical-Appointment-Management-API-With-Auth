package com.api.v2.medical_appointments;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MedicalAppointmentCompletionTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Order(1)
    void testSuccessfulCancellation() {
        String correctId = "";
        webTestClient
                .patch()
                .uri("api/v2/medical-appointments/%s/completion".formatted(correctId))
                .exchange()
                .expectStatus().is2xxSuccessful();
    }

    @Test
    @Order(2)
    void testUnsuccessfulCancellationDueToNotFoundId() {
        String wrongId = "123456789012345678901234";
        webTestClient
                .patch()
                .uri("api/v2/medical-appointments/%s/completion".formatted(wrongId))
                .exchange()
                .expectStatus().is5xxServerError();
    }
}
