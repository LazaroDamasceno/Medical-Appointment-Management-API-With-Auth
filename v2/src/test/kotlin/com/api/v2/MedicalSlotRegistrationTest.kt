package com.api.v2

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
import java.time.LocalDateTime
import java.util.UUID

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@AutoConfigureMockMvc
class MedicalSlotRegistrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Order(1)
    @Test
    fun shouldReturnCreatedWhenSuccessful() {
        val medicalLicenseNumber = ""
        val availableAt = LocalDateTime.of(2025,12,12,12,30,30)
        mockMvc.perform(
            post("/api/v2/medical-slots/$medicalLicenseNumber/$availableAt")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated)
    }

    @Order(2)
    @Test
    fun shouldReturnConflictWhenDoctorWasNotFound() {
        val medicalLicenseNumber = UUID
            .randomUUID()
            .toString()
            .replace("-", "")
            .substring(0, 10)
        val availableAt = LocalDateTime.of(2025,12,12,12,30,30)
        mockMvc.perform(
            post("/api/v2/medical-slots/$medicalLicenseNumber/$availableAt")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound)
    }

    @Order(3)
    @Test
    fun shouldReturnConflictWhenDuplicatedBookingDateTime() {
        val medicalLicenseNumber =  ""
        val availableAt = LocalDateTime.of(2025,12,12,12,30,30)
        mockMvc.perform(
            post("/api/v2/medical-slots/$medicalLicenseNumber/$availableAt")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isConflict)
    }
}