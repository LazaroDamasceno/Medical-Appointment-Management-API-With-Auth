package com.api.v2.medical_slots.domain

import com.api.v2.doctors.Doctor
import com.api.v2.medical_slots.MedicalSlot
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import java.time.LocalDateTime

interface MedicalSlotRepository: MongoRepository<MedicalSlot, String> {

    @Query("{ 'doctor': ?0, 'availableAt': ?1 }")
    fun findByDoctorAndAvailableAt(doctor: Doctor, availableAt: LocalDateTime): MedicalSlot?
}