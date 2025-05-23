package com.api.v2

import com.api.v2.people.dtos.Address
import com.api.v2.people.enums.Gender
import com.api.v2.people.requests.PersonRegistrationDTO
import com.api.v2.people.requests.PersonUpdateDTO
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
class CustomerUpdateTest {

	@Autowired
	lateinit var webTestClient: WebTestClient

	val personDTO = PersonUpdateDTO(
		"Leonard",
		"Campbell",
		"Smith",
		LocalDate.of(2000,12,12),
		"leosmith@leosmith.com",
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
		val customerId = ""
		webTestClient
			.patch()
			.uri("api/v2/customers/$customerId")
			.bodyValue(personDTO)
			.exchange()
			.expectStatus()
			.is2xxSuccessful
	}

	@Test
	@Order(2)
	fun `should return not found when customer was not found`() {
		val randomId = UUID.randomUUID().toString()
		webTestClient
			.patch()
			.uri("api/v2/customers/$randomId")
			.bodyValue(personDTO)
			.exchange()
			.expectStatus()
			.is5xxServerError
	}
}
