package com.api.v2;

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

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DoctorHiringTest {

	@Autowired
	private WebTestClient webTestClient;

	DoctorHiringDto hiringDto = new DoctorHiringDto(
			"12345678CA",
			new PersonRegistrationDto(
					"Leo",
					"",
					"Santos",
					LocalDate.parse("2000-12-12"),
					"123456789",
					"contact@leosantos.com",
					"1234567890",
					"male"
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
				.expectStatus()
				.is2xxSuccessful();
	}

	@Test
	@Order(2)
	void testUnsuccessfulHiring() {
		webTestClient
				.post()
				.uri("api/v2/doctors")
				.bodyValue(hiringDto)
				.exchange()
				.expectStatus()
				.is5xxServerError();
	}

	DoctorHiringDto hiringDto2 = new DoctorHiringDto(
			"12345677CA",
			new PersonRegistrationDto(
					"Leo",
					"",
					"Santos",
					LocalDate.parse("2000-12-12"),
					"123456789",
					"contact@leosantos.com",
					"1234567890",
					"male"
			)
	);

	@Test
	@Order(3)
	void testUnsuccessfulHiring2() {
		webTestClient
				.post()
				.uri("api/v2/doctors")
				.bodyValue(hiringDto2)
				.exchange()
				.expectStatus()
				.is5xxServerError();
	}

	DoctorHiringDto hiringDto3 = new DoctorHiringDto(
			"12345677CA",
			new PersonRegistrationDto(
					"Leo",
					"",
					"Santos",
					LocalDate.parse("2000-12-12"),
					"123456788",
					"contact@leosantos.com",
					"1234567890",
					"male"
			)
	);

	@Test
	@Order(4)
	void testUnsuccessfulHiring3() {
		webTestClient
				.post()
				.uri("api/v2/doctors")
				.bodyValue(hiringDto3)
				.exchange()
				.expectStatus()
				.is5xxServerError();
	}
}
