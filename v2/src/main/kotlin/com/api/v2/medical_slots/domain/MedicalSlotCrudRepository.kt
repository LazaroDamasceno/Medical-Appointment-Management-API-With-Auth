package com.api.v2.medical_slots.domain

import com.api.v2.medical_slots.MedicalSlot
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import java.time.LocalDateTime

interface MedicalSlotCrudRepository: MongoRepository<MedicalSlot, String> {

    @Query("{ 'doctor._id': ?0, 'availableAt': ?1 }")
    fun findByDoctorAndAvailableAt(doctorId: String, availableAt: LocalDateTime): MedicalSlot?

    @Query("{ '_id': ?0, 'doctor._id': ?1 }")
    fun findByDoctorAndId(id: String, doctorId: String): MedicalSlot?
}