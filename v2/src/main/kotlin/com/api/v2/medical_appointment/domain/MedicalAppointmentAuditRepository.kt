package com.api.v2.medical_appointment.domain

import org.springframework.data.mongodb.repository.MongoRepository

interface MedicalAppointmentAuditRepository: MongoRepository<MedicalAppointmentAuditTrail, String>