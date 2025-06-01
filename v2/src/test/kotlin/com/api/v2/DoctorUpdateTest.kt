package com.api.v2

import com.api.v2.people.dtos.Address
import com.api.v2.people.enums.Gender
import com.api.v2.people.PersonUpdateDTO
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
class DoctorUpdateTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    val personDTO = PersonUpdateDTO(
        "Wilson",
        "Campbell",
        "Wisconsin",
        LocalDate.of(2000, 12, 12),
        "wilson@drwisconsin.com",
        "1234567890",
        Gender.MALE,
        Address(
            "Downtown",
            "LA",
            "90012"
        )
    )

    @Order(1)
    @Test
    fun `should return no content when successful`() {
        val licenseNumber = ""
        mockMvc.perform(
            patch("/api/v2/doctors/$licenseNumber/updating")
                .content(objectMapper.writeValueAsString(personDTO))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent)
    }

    @Order(2)
    @Test
    fun `should return not found when doctor was not found`() {
        val licenseNumber = UUID.randomUUID()
            .toString()
            .replace("-", "")
            .substring(0, 10)
        mockMvc.perform(
            patch("/api/v2/doctors/$licenseNumber/updating")
                .content(objectMapper.writeValueAsString(personDTO))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound)
    }

}