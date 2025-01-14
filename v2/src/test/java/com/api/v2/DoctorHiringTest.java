package com.api.v2;

import com.api.v2.customers.dtos.CustomerResponseDto;
import com.api.v2.doctors.dtos.DoctorHiringDto;
import com.api.v2.people.dtos.PersonRegistrationDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DoctorHiringTest {

    @Autowired
    private WebTestClient webTestClient;

    private final DoctorHiringDto hiringDto = new DoctorHiringDto(
            "12345678CA",
            new PersonRegistrationDto(
                    "Gabriel",
                    "",
                    "Nazar",
                    LocalDate.parse("2000-12-12"),
                    "987654321",
                    "g.nazar@drnazar.com",
                    "1234567890",
                    "male"
            )
    );

    @Test
    @Order(1)
    void testSuccessfulRegistration() {
        webTestClient
                .post()
                .uri("api/v2/doctors")
                .bodyValue(hiringDto)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(CustomerResponseDto.class);
    }

    @Test
    @Order(2)
    void testUnsuccessfulRegistrationDueToDuplicatedMedicalLicenseNumber() {
        webTestClient
                .post()
                .uri("api/v2/doctors")
                .bodyValue(hiringDto)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    private final DoctorHiringDto hiringDto2 = new DoctorHiringDto(
            "12345677CA",
            new PersonRegistrationDto(
                    "Gabriel",
                    "",
                    "Nazar",
                    LocalDate.parse("2000-12-12"),
                    "987654321",
                    "g.nazar@drnazar.com",
                    "1234567890",
                    "male"
            )
    );

    @Test
    @Order(3)
    void testUnsuccessfulRegistrationDueToDuplicatedMedicalSsn() {
        webTestClient
                .post()
                .uri("api/v2/doctors")
                .bodyValue(hiringDto2)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    private final DoctorHiringDto hiringDto3 = new DoctorHiringDto(
            "12345677CA",
            new PersonRegistrationDto(
                    "Gabriel",
                    "",
                    "Nazar",
                    LocalDate.parse("2000-12-12"),
                    "987654320",
                    "g.nazar@drnazar.com",
                    "1234567890",
                    "male"
            )
    );

    @Test
    @Order(4)
    void testUnsuccessfulRegistrationDueToDuplicatedMedicalEmail() {
        webTestClient
                .post()
                .uri("api/v2/doctors")
                .bodyValue(hiringDto3)
                .exchange()
                .expectStatus().is5xxServerError();
    }

}
