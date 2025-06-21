package com.api.v2.medical_appointment.exceptions

class MedicalAppointmentNotFoundException(id: String)
    : RuntimeException("Medical appointment whose id is $id was not found.")