package com.api.v1;

import com.api.v1.common.States;
import com.api.v1.doctors.requests.DoctorRegistrationDto;
import com.api.v1.people.dtos.Address;
import com.api.v1.people.enums.Gender;
import com.api.v1.people.requests.PersonRegistrationDto;
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
class DoctorRegistrationIntegrationTest {

	@Autowired
	WebTestClient webTestClient;

	DoctorRegistrationDto registrationDto = new DoctorRegistrationDto(

	);

	@Test
	@Order(1)
	void testSuccessfulRegistration() {
		webTestClient
				.post()
				.uri("api/v1/customers")
				.bodyValue(registrationDto)
				.exchange()
				.expectStatus()
				.is2xxSuccessful();
	}

	PersonRegistrationDto registrationDtoWithDuplicatedSSn = new PersonRegistrationDto(
			"Leonard",
			"",
			"Smith",
			"123456789",
			LocalDate.of(2000,12,12),
			"1234567890",
			Gender.CIS_MALE,
			"leosmith@mail.com",
			new Address(
					"Downtown",
					"Los Angeles",
					States.CA,
					"90012"
			)
	);
}
