package com.api.v2.medical_appointment.domain

import com.api.v2.medical_appointment.MedicalAppointment
import org.springframework.data.mongodb.repository.MongoRepository

interface MedicalAppointmentCrudRepository: MongoRepository<MedicalAppointment, String>