package com.api.v2.medical_appointment

import com.api.v2.customers.Customer

interface MedicalAppointmentFinder {
    fun findById(id: String): MedicalAppointment
    fun findByIdAndCustomer(id: String, customer: Customer): MedicalAppointment
}