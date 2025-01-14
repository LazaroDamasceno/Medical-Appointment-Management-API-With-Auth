package com.api.v2;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MedicalSlotCancellationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Order(1)
    void testSuccessfulCancellation() {
        String correctId = "6786a65cca6e0555a82e04bc";
        webTestClient
                .patch()
                .uri("api/v2/medical-slots/%s".formatted(correctId))
                .exchange()
                .expectStatus().is2xxSuccessful();
    }

    @Test
    @Order(2)
    void testUnsuccessfulCancellationDueToNotFoundId() {
        String wrongId = "6786a65cca6e0555a82e04bd";
        webTestClient
                .patch()
                .uri("api/v2/medical-slots/%s".formatted(wrongId))
                .exchange()
                .expectStatus().is2xxSuccessful();
    }
}
