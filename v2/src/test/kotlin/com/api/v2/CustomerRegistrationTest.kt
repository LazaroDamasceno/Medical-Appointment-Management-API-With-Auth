package com.api.v2

import com.api.v2.people.dtos.Address
import com.api.v2.people.enums.Gender
import com.api.v2.people.requests.PersonRegistrationDTO
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import java.time.LocalDate
import java.util.UUID

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerRegistrationTest {

	@Autowired
	lateinit var webTestClient: WebTestClient

	val personDTO = PersonRegistrationDTO(
		"Leonard",
		"",
		"Smith",
		LocalDate.of(2000,12,12),
		UUID.randomUUID()
			.toString()
			.replace("-", "")
			.subSequence(0, 10)
			.toString(),
		"leosmith@mail.com",
		"1234567890",
		Gender.MALE,
		Address(
			"Downtown",
			"LA",
			"90012"
		)
	)

	@Test
	@Order(1)
	fun `should return created when successful`() {
		webTestClient
			.post()
			.uri("api/v2/customers")
			.bodyValue(personDTO)
			.exchange()
			.expectStatus()
			.is2xxSuccessful
	}

	@Test
	@Order(2)
	fun `should return conflict when SIN is duplicated`() {
		webTestClient
			.post()
			.uri("api/v2/customers")
			.bodyValue(personDTO)
			.exchange()
			.expectStatus()
			.is4xxClientError
	}

	val duplicatedEmailDTO = PersonRegistrationDTO(
		"Leonard",
		"",
		"Smith",
		LocalDate.of(2000, 12, 12),
		UUID.randomUUID()
			.toString()
			.replace("-", "")
			.subSequence(0, 10)
			.toString(),
		"leosmith@mail.com",
		"1234567890",
		Gender.MALE,
		Address(
			"Downtown",
			"LA",
			"90012"
		)
	)

	@Test
	@Order(2)
	fun `should return conflict when email is duplicated`() {
		webTestClient
			.post()
			.uri("api/v2/customers")
			.bodyValue(duplicatedEmailDTO)
			.exchange()
			.expectStatus()
			.is4xxClientError
	}

}
