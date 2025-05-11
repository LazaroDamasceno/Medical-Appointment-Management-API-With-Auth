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
public class MedicalSlotCancellationIntegrationTest {

    @Autowired
    WebTestClient webTestClient;

    @Order(1)
    @Test
    void testSuccessfulCancellation() {
        String doctorLicenseNumber = "";
        String slotId = "";
        webTestClient
                .patch()
                .uri("api/v1/medical-slots/%s/%s/cancellation".formatted(doctorLicenseNumber, slotId))
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    @Order(2)
    @Test
    void testUnsuccessfulCancellation_DoctorNotFound() {
        String doctorLicenseNumber = UUID.randomUUID().toString();
        String slotId = UUID.randomUUID().toString();
        webTestClient
                .patch()
                .uri("api/v1/medical-slots/%s/%s/cancellation".formatted(doctorLicenseNumber, slotId))
                .exchange()
                .expectStatus()
                .is4xxClientError();
    }

    @Order(3)
    @Test
    void testUnsuccessfulCancellation_MedicalSlotNotFound() {
        String doctorLicenseNumber = UUID.randomUUID().toString();
        String slotId = UUID.randomUUID().toString();
        webTestClient
                .patch()
                .uri("api/v1/medical-slots/%s/%s/cancellation".formatted(doctorLicenseNumber, slotId))
                .exchange()
                .expectStatus()
                .is4xxClientError();
    }

    @Order(4)
    @Test
    void testUnsuccessfulCancellation_NotAssociatedDoctor() {
        String doctorLicenseNumber = "";
        String slotId = "";
        webTestClient
                .patch()
                .uri("api/v1/medical-slots/%s/%s/cancellation".formatted(doctorLicenseNumber, slotId))
                .exchange()
                .expectStatus()
                .is4xxClientError();
    }

    @Order(5)
    @Test
    void testUnsuccessfulCancellation_CanceledMedicalSlot() {
        String doctorLicenseNumber = "";
        String slotId = "";
        webTestClient
                .patch()
                .uri("api/v1/medical-slots/%s/%s/cancellation".formatted(doctorLicenseNumber, slotId))
                .exchange()
                .expectStatus()
                .is4xxClientError();
    }

    @Order(6)
    @Test
    void testUnsuccessfulCancellation_CompletedMedicalSlot() {
        String doctorLicenseNumber = "";
        String slotId = "";
        webTestClient
                .patch()
                .uri("api/v1/medical-slots/%s/%s/cancellation".formatted(doctorLicenseNumber, slotId))
                .exchange()
                .expectStatus()
                .is4xxClientError();
    }

}
