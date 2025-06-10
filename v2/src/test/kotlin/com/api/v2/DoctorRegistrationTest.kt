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
class DoctorRegistrationTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    val licenseNumber = UUID
        .randomUUID()
        .toString()
        .replace("-", "")
        .substring(0, 10)

    val personDTO = PersonRegistrationDTO(
        "Wilson",
        "",
        "Wisconsin",
        LocalDate.of(2000,12,12),
        UUID
            .randomUUID()
            .toString()
            .replace("-", "")
            .substring(0, 10),
        "wilsonwisconsin@mail.com",
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
             post("/api/v2/doctors/$licenseNumber")
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(objectMapper.writeValueAsString(personDTO))
         ).andExpect(status().isCreated)
     }

    @Test
    @Order(2)
    fun `should return conflict when medical license number is duplicated`() {
        mockMvc.perform(
            post("/api/v2/doctors/$licenseNumber")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(personDTO))
        ).andExpect(status().isConflict)
    }

    val duplicatedSIN = PersonRegistrationDTO(
        "Wilson",
        "",
        "Wisconsin",
        LocalDate.of(2000,12,12),
        UUID
            .randomUUID()
            .toString()
            .replace("-", "")
            .substring(0, 10),
        "wilsonwisconsin@mail.com",
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
    fun `should return conflict when SIN is duplicated`() {
        val randomLicenseNumber = UUID
            .randomUUID()
            .toString()
            .replace("-", "")
            .substring(0, 10)
        mockMvc.perform(
            post("/api/v2/doctors/$randomLicenseNumber")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(duplicatedSIN))
        ).andExpect(status().isConflict)
    }

    val duplicatedEmail = PersonRegistrationDTO(
        "Wilson",
        "",
        "Wisconsin",
        LocalDate.of(2000,12,12),
        UUID
            .randomUUID()
            .toString()
            .replace("-", "")
            .substring(0, 10),
        "wilsonwisconsin@mail.com",
        "1234567890",
        Gender.MALE,
        Address(
            "Downtown",
            "LA",
            "90012"
        )
    )

    @Test
    @Order(4)
    fun `should return conflict when email is duplicated`() {
        val randomLicenseNumber = UUID
            .randomUUID()
            .toString()
            .replace("-", "")
            .substring(0, 10)
        mockMvc.perform(
            post("/api/v2/doctors/$randomLicenseNumber")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(duplicatedEmail))
        ).andExpect(status().isConflict)
    }
}