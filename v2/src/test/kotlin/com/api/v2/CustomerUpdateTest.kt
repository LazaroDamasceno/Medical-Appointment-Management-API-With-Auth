package com.api.v2

import com.api.v2.people.dtos.Address
import com.api.v2.people.enums.Gender
import com.api.v2.people.requests.PersonUpdateDTO
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDate
import java.util.UUID

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CustomerUpdateTest {

	@Autowired
	lateinit var mockMvc: MockMvc

	@Autowired
	lateinit var objectMapper: ObjectMapper

	val personDTO = PersonUpdateDTO(
		"Leonard",
		"Campbell",
		"Smith",
		LocalDate.of(2000,12,12),
		"lms@mail.com",
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
	fun `should return no content when successful`() {
		val id = "dcb25146-2121-4119-88f3-6f427dfd0d9c"
		mockMvc.perform(
			patch("/api/v2/customers/$id")
				.content(objectMapper.writeValueAsString(personDTO))
				.contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isNoContent)
	}

	@Test
	@Order(2)
	fun `should return not found when customer was not found`() {
		val randomId = UUID.randomUUID().toString()
		mockMvc.perform(
			patch("/api/v2/customers/$randomId")
				.content(objectMapper.writeValueAsString(personDTO))
				.contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isNotFound)
	}
}
