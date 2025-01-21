package com.api.v2.medical_slots;

import com.api.v2.medical_slots.dtos.MedicalSlotRegistrationDto;
import com.api.v2.medical_slots.dtos.MedicalSlotResponseDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MedicalSlotRegistrationTest {

    @Autowired
    private WebTestClient webTestClient;

    private final MedicalSlotRegistrationDto registrationDto = new MedicalSlotRegistrationDto(
            "12345678CA",
            LocalDateTime.parse("2025-12-12T12:30:30")
    );

    @Test
    @Order(1)
    void testSuccessfulRegistration() {
        webTestClient
                .post()
                .uri("api/v2/medical-slots")
                .bodyValue(registrationDto)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(MedicalSlotResponseDto.class);
    }

    @Test
    @Order(2)
    void testUnsuccessfulRegistrationDueToDuplicatedBookingDateTime() {
        webTestClient
                .post()
                .uri("api/v2/medical-slots")
                .bodyValue(registrationDto)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    private final MedicalSlotRegistrationDto registrationDto2 = new MedicalSlotRegistrationDto(
            "12345677CA",
            LocalDateTime.parse("2025-12-12T12:30:30")
    );

    @Test
    @Order(3)
    void testUnsuccessfulRegistrationDueToNotFoundDoctor() {
        webTestClient
                .post()
                .uri("api/v2/medical-slots")
                .bodyValue(registrationDto2)
                .exchange()
                .expectStatus().is5xxServerError();
    }

}
