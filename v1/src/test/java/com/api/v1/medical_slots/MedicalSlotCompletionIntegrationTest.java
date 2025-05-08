package com.api.v1.medical_slots;

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
public class MedicalSlotCompletionIntegrationTest {

    @Autowired
    WebTestClient webTestClient;

    @Order(1)
    @Test
    void testSuccessfulCompletion() {
        String doctorId = "";
        String slotId = "";
        webTestClient
                .patch()
                .uri("api/v1/medical-slots/%s/%s/completion".formatted(doctorId, slotId))
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    @Order(2)
    @Test
    void testUnsuccessfulCompletion_DoctorNotFound() {
        String doctorId = UUID.randomUUID().toString();
        String slotId = UUID.randomUUID().toString();
        webTestClient
                .patch()
                .uri("api/v1/medical-slots/%s/%s/completion".formatted(doctorId, slotId))
                .exchange()
                .expectStatus()
                .is5xxServerError();
    }

    @Order(3)
    @Test
    void testUnsuccessfulCompletion_MedicalSlotNotFound() {
        String doctorId = UUID.randomUUID().toString();
        String slotId = UUID.randomUUID().toString();
        webTestClient
                .patch()
                .uri("api/v1/medical-slots/%s/%s/completion".formatted(doctorId, slotId))
                .exchange()
                .expectStatus()
                .is5xxServerError();
    }

    @Order(4)
    @Test
    void testUnsuccessfulCompletion_NotAssociatedDoctor() {
        String doctorId = "";
        String slotId = "";
        webTestClient
                .patch()
                .uri("api/v1/medical-slots/%s/%s/completion".formatted(doctorId, slotId))
                .exchange()
                .expectStatus()
                .is5xxServerError();
    }

    @Order(5)
    @Test
    void testUnsuccessfulCompletion_CanceledMedicalSlot() {
        String doctorId = "";
        String slotId = "";
        webTestClient
                .patch()
                .uri("api/v1/medical-slots/%s/%s/completion".formatted(doctorId, slotId))
                .exchange()
                .expectStatus()
                .is5xxServerError();
    }

    @Order(6)
    @Test
    void testUnsuccessfulCompletion_CompletedMedicalSlot() {
        String doctorId = "";
        String slotId = "";
        webTestClient
                .patch()
                .uri("api/v1/medical-slots/%s/%s/completion".formatted(doctorId, slotId))
                .exchange()
                .expectStatus()
                .is5xxServerError();
    }

}
