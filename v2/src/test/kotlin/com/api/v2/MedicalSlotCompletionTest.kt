package com.api.v2

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.UUID

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class MedicalSlotCompletionTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    @Order(1)
    fun `should return no content when successful`() {
        val medicalLicenseNumber = ""
        val slotId = ""
        mockMvc.perform(
            patch("/api/v2/medical-slots/$medicalLicenseNumber/$slotId/completion")
        ).andExpect(status().isNoContent)
    }

    @Test
    @Order(2)
    fun `should return no found when doctor was not found`() {
        val medicalLicenseNumber = UUID
            .randomUUID()
            .toString()
            .replace("-", "")
            .substring(0, 10)
        val slotId = ""
        mockMvc.perform(
            patch("/api/v2/medical-slots/$medicalLicenseNumber/$slotId/completion")
        ).andExpect(status().isNotFound)
    }

    @Test
    @Order(3)
    fun `should return no found when medical slot was not found`() {
        val medicalLicenseNumber = ""
        val slotId = UUID
            .randomUUID()
            .toString()
        mockMvc.perform(
            patch("/api/v2/medical-slots/$medicalLicenseNumber/$slotId/completion")
        ).andExpect(status().isNotFound)
    }

    @Test
    @Order(4)
    fun `should return conflict when doctor is not associated with medical slot`() {
        val medicalLicenseNumber = ""
        val slotId = ""
        mockMvc.perform(
            patch("/api/v2/medical-slots/$medicalLicenseNumber/$slotId/completion")
        ).andExpect(status().isConflict)
    }

    @Test
    @Order(5)
    fun `should return conflict when medical slot is currently cancelled`() {
        val medicalLicenseNumber = ""
        val slotId = ""
        mockMvc.perform(
            patch("/api/v2/medical-slots/$medicalLicenseNumber/$slotId/completion")
        ).andExpect(status().isConflict)
    }

    @Test
    @Order(6)
    fun `should return conflict when medical slot is currently completed`() {
        val medicalLicenseNumber = ""
        val slotId = ""
        mockMvc.perform(
            patch("/api/v2/medical-slots/$medicalLicenseNumber/$slotId/completion")
        ).andExpect(status().isConflict)
    }
}