package com.api.v2.doctors;

import com.api.v2.doctors.dtos.DoctorHiringDto;
import com.api.v2.doctors.dtos.DoctorResponseDto;
import com.api.v2.people.dtos.PersonRegistrationDto;
import com.api.v2.people.utils.Gender;
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
public class DoctorHiring2Test {

    @Autowired
    private WebTestClient webTestClient;

    private final DoctorHiringDto hiringDto = new DoctorHiringDto(
            "12345678CA",
            new PersonRegistrationDto(
                    "Leonard",
                    "",
                    "Silva",
                    LocalDate.parse("2000-12-12"),
                    "987654321",
                    "leonardsilva@mail.com",
                    "1234567890",
                    Gender.CIS_MALE
            )
    );

    @Test
    @Order(1)
    void testSuccessfulHiring() {
        webTestClient
                .post()
                .uri("api/v2/doctors")
                .bodyValue(hiringDto)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(DoctorResponseDto.class);
    }

    @Test
    @Order(2)
    void testUnsuccessfulHiringDueToDuplicatedMedicalLicenseNumber() {
        webTestClient
                .post()
                .uri("api/v2/doctors")
                .bodyValue(hiringDto)
                .exchange()
                .expectStatus().is4xxClientError();
    }

    private final DoctorHiringDto hiringDto2 = new DoctorHiringDto(
            "12345677CA",
            new PersonRegistrationDto(
                    "Leonard",
                    "",
                    "Silva",
                    LocalDate.parse("2000-12-12"),
                    "987654321",
                    "leonardsilva@mail.com",
                    "1234567890",
                    Gender.CIS_MALE
            )
    );

    @Test
    @Order(3)
    void testUnsuccessfulHiringDueToDuplicatedSsn() {
        webTestClient
                .post()
                .uri("api/v2/doctors")
                .bodyValue(hiringDto2)
                .exchange()
                .expectStatus().is4xxClientError();
    }

    private final DoctorHiringDto hiringDto3 = new DoctorHiringDto(
            "12345676CA",
            new PersonRegistrationDto(
                    "Leonard",
                    "",
                    "Silva",
                    LocalDate.parse("2000-12-12"),
                    "123456789",
                    "leonardsilva@mail.com",
                    "1234567890",
                    Gender.CIS_MALE
            )
    );

    @Test
    @Order(4)
    void testUnsuccessfulHiringDueToEmail() {
        webTestClient
                .post()
                .uri("api/v2/doctors")
                .bodyValue(hiringDto3)
                .exchange()
                .expectStatus().is4xxClientError();
    }

}
