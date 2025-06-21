package com.api.v2.medical_appointment.exceptions

class CancelledMedicalAppointmentException(id: String)
    : RuntimeException("Medical appointment whose id is $id is currently cancelled.")