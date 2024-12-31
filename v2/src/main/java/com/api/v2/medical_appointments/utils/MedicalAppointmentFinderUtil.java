package com.api.v2.medical_appointments.utils;

import com.api.v2.customers.domain.Customer;
import com.api.v2.doctors.domain.Doctor;
import com.api.v2.medical_appointments.domain.MedicalAppointment;
import com.api.v2.medical_appointments.domain.MedicalAppointmentRepository;
import com.api.v2.medical_appointments.exceptions.NonExistentMedicalAppointmentException;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
public class MedicalAppointmentFinderUtil {

    private final MedicalAppointmentRepository repository;

    public MedicalAppointmentFinderUtil(MedicalAppointmentRepository repository) {
        this.repository = repository;
    }

    public Mono<MedicalAppointment> findById(String id) {
        return repository
                .findById(new ObjectId(id))
                .singleOptional()
                .flatMap(optional -> {
                   if (optional.isEmpty()) {
                       return Mono.error(new NonExistentMedicalAppointmentException(id));
                   }
                   return Mono.just(optional.get());
                });
    }

    public Mono<MedicalAppointment> findActiveByCustomerAndDoctorAndBookedAt(
            Customer customer,
            Doctor doctor,
            LocalDateTime bookedAt
    ) {
        return repository
                .findAll()
                .filter(slot ->
                        slot.getCanceledAt() == null
                        && slot.getCompletedAt() == null
                        && slot.getCustomer().equals(customer)
                        && slot.getDoctor().equals(doctor)
                        && slot.getBookedAt().equals(bookedAt)
                )
                .singleOrEmpty();
    }
}
