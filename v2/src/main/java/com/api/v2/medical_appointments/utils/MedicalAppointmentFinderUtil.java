package com.api.v2.medical_appointments.utils;

import com.api.v2.medical_appointments.domain.MedicalAppointment;
import com.api.v2.medical_appointments.domain.MedicalAppointmentRepository;
import com.api.v2.medical_appointments.exceptions.NonExistentMedicalAppointmentException;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

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
}
