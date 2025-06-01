package com.api.v2

import com.api.v2.people.dtos.Address
import com.api.v2.people.enums.Gender
import com.api.v2.people.PersonRegistrationDTO
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDate
import java.util.UUID

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CustomerRegistrationTest {

	@Autowired
	lateinit var mockMvc: MockMvc

	@Autowired
	lateinit var objectMapper: ObjectMapper

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
		mockMvc.perform(
			post("/api/v2/customers")
				.content(objectMapper.writeValueAsString(personDTO))
				.contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isCreated)
	}

	@Test
	@Order(2)
	fun `should return conflict when SIN is duplicated`() {
		mockMvc.perform(
			post("/api/v2/customers")
				.content(objectMapper.writeValueAsString(personDTO))
				.contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isConflict)
	}

	val duplicatedEmailDTO = PersonRegistrationDTO(
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
	@Order(3)
	fun `should return conflict when email is duplicated`() {
		mockMvc.perform(
			post("/api/v2/customers")
				.content(objectMapper.writeValueAsString(duplicatedEmailDTO))
				.contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isConflict)
	}
}
