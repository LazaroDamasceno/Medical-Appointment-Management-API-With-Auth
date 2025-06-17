package com.api.v1.medical_appointments.domain;

import com.api.v1.medical_appointments.MedicalAppointment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MedicalAppointmentCrudRepository extends MongoRepository<MedicalAppointment, String> {
}
