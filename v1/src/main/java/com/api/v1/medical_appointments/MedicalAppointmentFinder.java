package com.api.v1.medical_appointments;

import com.api.v1.customers.Customer;
import com.api.v1.doctors.Doctor;

public interface MedicalAppointmentFinder {
    MedicalAppointment findById(String id);
    MedicalAppointment findByIdAndCustomer(String id, Customer customer);
    MedicalAppointment findByIdAndDoctor(String id, Doctor doctor);
}
