package com.api.v2;

import com.api.v2.common.AddressDto;
import com.api.v2.dtos.CustomerModificationDto;
import com.api.v2.dtos.CustomerRegistrationDto;
import com.api.v2.dtos.PersonModificationDto;
import com.api.v2.dtos.PersonRegistrationDto;
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
class CustomerModificationTest {

	@Autowired
	private WebTestClient webTestClient;

	CustomerModificationDto modificationDto = new CustomerModificationDto(
			new PersonModificationDto(
					"Leonardo",
					"Silva",
					"Santos Jr.",
					LocalDate.parse("2000-12-12"),
					"jr@leosantos.com",
					"1234567890",
					"cis male"
			),
			new AddressDto(
					"CA",
					"Sacramento",
					"Downtown",
					"90012"
			)

	);

	@Test
	@Order(1)
	void testSuccessfulRegistration() {
		String ssn = "123456789";
		webTestClient
				.patch()
				.uri("api/v2/customers/%s".formatted(ssn))
				.bodyValue(modificationDto)
				.exchange()
				.expectStatus()
				.is2xxSuccessful();
	}

	@Test
	@Order(2)
	void testUnsuccessfulRegistration() {
		String ssn = "123456788";
		webTestClient
				.patch()
				.uri("api/v2/customers/%s".formatted(ssn))
				.bodyValue(modificationDto)
				.exchange()
				.expectStatus()
				.is5xxServerError();
	}

}
