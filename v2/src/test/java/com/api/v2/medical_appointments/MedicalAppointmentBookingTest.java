package com.api.v2.medical_appointments;

import com.api.v2.medical_appointments.dtos.MedicalAppointmentBookingDto;
import com.api.v2.medical_appointments.dtos.MedicalAppointmentResponseDto;
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
public class MedicalAppointmentBookingTest {

    @Autowired
    private WebTestClient webTestClient;

    private final MedicalAppointmentBookingDto bookingDto = new MedicalAppointmentBookingDto(
            "123456789",
            "12345678CA",
            LocalDateTime.parse("2025-12-12T12:30:30")
    );

    @Order(1)
    @Test
    void testSuccessfulBooking() {
        webTestClient
                .post()
                .uri("api/v2/medical-appointments/public-insurance")
                .bodyValue(bookingDto)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(MedicalAppointmentResponseDto.class);
    }

    @Order(2)
    @Test
    void testUnsuccessfulBookingDueToDuplicatedBookingDateTime() {
        webTestClient
                .post()
                .uri("api/v2/medical-appointments/public-insurance")
                .bodyValue(bookingDto)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    private final MedicalAppointmentBookingDto bookingDto2 = new MedicalAppointmentBookingDto(
            "123456788",
            "12345678CA",
            LocalDateTime.parse("2025-12-12T12:30:30")
    );

    @Order(3)
    @Test
    void testUnsuccessfulBookingDueToNotFoundCustomer() {
        webTestClient
                .post()
                .uri("api/v2/medical-appointments/public-insurance")
                .bodyValue(bookingDto2)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    private final MedicalAppointmentBookingDto bookingDto3 = new MedicalAppointmentBookingDto(
            "123456789",
            "12345677CA",
            LocalDateTime.parse("2025-12-12T12:30:30")
    );

    @Order(4)
    @Test
    void testUnsuccessfulBookingDueToNotFoundDoctor() {
        webTestClient
                .post()
                .uri("api/v2/medical-appointments/public-insurance")
                .bodyValue(bookingDto3)
                .exchange()
                .expectStatus().is5xxServerError();
    }
}
