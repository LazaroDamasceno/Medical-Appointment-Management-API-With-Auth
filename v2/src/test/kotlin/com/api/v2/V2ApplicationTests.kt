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
class V2ApplicationTests {

	@Autowired
	lateinit var webTestClient: WebTestClient

	val registrationDto = PersonRegistrationDto(
		"Leo",
		"",
		"Silva",
		LocalDate.of(2000,12,12),
		"1234567890",
		"leosilva@mail.com",
		"1234567890",
		Gender.TRANS_FEMALE,
		Address("LA", "LA", "90012")
	)

	@Test
	@Order(1)
	fun contextLoads() {
		webTestClient
			.post()
			.uri("v2/people")
			.bodyValue(registrationDto)
			.exchange()
			.expectStatus()
			.is2xxSuccessful
	}

}
