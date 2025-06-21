package com.api.v2.medical_appointment.utils

import com.api.v2.customers.Customer
import com.api.v2.medical_appointment.MedicalAppointment
import com.api.v2.medical_appointment.MedicalAppointmentFinder
import com.api.v2.medical_appointment.domain.MedicalAppointmentCrudRepository
import com.api.v2.medical_appointment.exceptions.InaccessibleMedicalAppointmentException
import com.api.v2.medical_appointment.exceptions.MedicalAppointmentNotFoundException
import org.springframework.stereotype.Service

@Service
class MedicalAppointmentFinderImpl(
    private val crudRepository: MedicalAppointmentCrudRepository
): MedicalAppointmentFinder {

    override fun findById(id: String): MedicalAppointment {
        val foundAppointment = crudRepository.findById(id)
        if (foundAppointment.isEmpty) {
            throw MedicalAppointmentNotFoundException(id)
        }
        return foundAppointment.get()
    }

    override fun findByIdAndCustomer(id: String, customer: Customer): MedicalAppointment {
        val foundAppointment = findById(id)
        if (foundAppointment.customer.id == customer.id) {
            val message = "Customer whose id is ${customer.id} cannot access the medical appointment whose id is $id."
            throw InaccessibleMedicalAppointmentException(message)
        }
        return foundAppointment
    }
}