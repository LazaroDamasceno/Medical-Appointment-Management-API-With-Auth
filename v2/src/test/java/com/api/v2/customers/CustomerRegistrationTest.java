package com.api.v2.customers;

import com.api.v2.common.AddressDto;
import com.api.v2.customers.dtos.CustomerRegistrationDto;
import com.api.v2.customers.dtos.CustomerResponseDto;
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
public class CustomerRegistrationTest {

    @Autowired
    private WebTestClient webTestClient;

    private final CustomerRegistrationDto registrationDto = new CustomerRegistrationDto(
            new PersonRegistrationDto(
                    "Leo",
                    "",
                    "Santos",
                    LocalDate.parse("2000-12-12"),
                    "123456789",
                    "leosantos@mail.com",
                    "1234567890",
                    "male"
            ),
            new AddressDto(
                    "LA",
                    "CA",
                    "Downtown",
                    "90012"
            )
    );

    @Test
    @Order(1)
    void testSuccessfulRegistration() {
        webTestClient
                .post()
                .uri("api/v2/customers")
                .bodyValue(registrationDto)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(CustomerResponseDto.class);
    }

    @Test
    @Order(2)
    void testUnsuccessfulRegistrationDueToDuplicatedSsn() {
        webTestClient
                .post()
                .uri("api/v2/customers")
                .bodyValue(registrationDto)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    private final CustomerRegistrationDto registrationDto2 = new CustomerRegistrationDto(
            new PersonRegistrationDto(
                    "Leo",
                    "",
                    "Santos",
                    LocalDate.parse("2000-12-12"),
                    "123456788",
                    "leosantos@mail.com",
                    "1234567890",
                    "male"
            ),
            new AddressDto(
                    "LA",
                    "CA",
                    "Downtown",
                    "90012"
            )
    );

    @Test
    @Order(3)
    void testUnsuccessfulRegistrationDueToDuplicatedEmail() {
        webTestClient
                .post()
                .uri("api/v2/customers")
                .bodyValue(registrationDto2)
                .exchange()
                .expectStatus().is5xxServerError();
    }
}
