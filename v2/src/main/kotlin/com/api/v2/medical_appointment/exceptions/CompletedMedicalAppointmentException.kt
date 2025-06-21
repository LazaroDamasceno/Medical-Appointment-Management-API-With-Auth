package com.api.v2.medical_appointment.exceptions

class CompletedMedicalAppointmentException(id: String)
    : RuntimeException("Medical appointment whose id is $id is currently completed.")