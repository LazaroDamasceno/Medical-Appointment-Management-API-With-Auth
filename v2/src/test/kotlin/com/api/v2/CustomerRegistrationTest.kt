package com.api.v2

import com.api.v2.people.dtos.Address
import com.api.v2.people.enums.Gender
import com.api.v2.people.requests.PersonRegistrationDto
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import java.time.LocalDate

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerRegistrationTest {

	@Autowired
	lateinit var webTestClient: WebTestClient

	val dto = PersonRegistrationDto(
		"Leonard",
		"",
		"Smith",
		"1234567890",
		LocalDate.of(2000,12,12),
		Gender.MALE,
		Address(
			"Downtown",
			"LA",
			"90012"
		),
		"leosmith@mail.com",
		"1234567890"
	)

	@Test
	@Order(1)
	fun testSuccessfulRegistration() {
		webTestClient
			.post()
			.uri("api/v2/customers")
			.bodyValue(dto)
			.exchange()
			.expectStatus()
			.is2xxSuccessful
	}

	@Test
	@Order(2)
	fun testUnsuccessfulRegistration_DuplicatedSIN() {
		webTestClient
			.post()
			.uri("api/v2/customers")
			.bodyValue(dto)
			.exchange()
			.expectStatus()
			.is4xxClientError
	}

	val dtoWithDuplicatedEmail = PersonRegistrationDto(
		"Leonard",
		"",
		"Smith",
		"0987654321",
		LocalDate.of(2000,12,12),
		Gender.MALE,
		Address(
			"Downtown",
			"LA",
			"90012"
		),
		"leosmith@mail.com",
		"1234567890"
	)

	@Test
	@Order(3)
	fun testUnsuccessfulRegistration_DuplicatedEmail() {
		webTestClient
			.post()
			.uri("api/v2/customers")
			.bodyValue(dtoWithDuplicatedEmail)
			.exchange()
			.expectStatus()
			.is4xxClientError
	}
}
