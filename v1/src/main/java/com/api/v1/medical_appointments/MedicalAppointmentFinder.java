package com.api.v1.medical_appointments;

public interface MedicalAppointmentFinder {
    MedicalAppointment findById(String id);
    MedicalAppointment findByIdAndCustomer(String id, String customerId);
}
