package com.api.v2.medical_slots.utils;

import com.api.v2.common.Id;
import com.api.v2.doctors.domain.Doctor;
import com.api.v2.medical_appointments.domain.MedicalAppointment;
import com.api.v2.medical_slots.domain.MedicalSlot;
import com.api.v2.medical_slots.domain.MedicalSlotRepository;
import com.api.v2.medical_slots.exceptions.NonExistentMedicalSlotException;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

@Component
public class MedicalSlotFinderUtil {

    private final MedicalSlotRepository repository;

    public MedicalSlotFinderUtil(MedicalSlotRepository repository) {
        this.repository = repository;
    }

    public Mono<MedicalSlot> findById(@Id String id) {
        return repository
                .findById(new ObjectId(id))
                .switchIfEmpty(Mono.error(new NonExistentMedicalSlotException(id)));
    }

    public Mono<MedicalSlot> findActiveByDoctorAndAvailableAt(Doctor doctor,
                                                              LocalDateTime availableAt,
                                                              ZoneId availableAtZoneId,
                                                              ZoneOffset availableAtZoneOffset
    ) {
        return repository
                .findAll()
                .filter(slot ->
                        slot.getCanceledAt() == null
                        && slot.getCompletedAt() == null
                        && slot.getDoctor().getId().equals(doctor.getId())
                        && slot.getAvailableAt().equals(availableAt)
                        && slot.getAvailableAtZoneId().equals(availableAtZoneId)
                        && slot.getAvailableAtZoneOffset().equals(availableAtZoneOffset)
                )
                .singleOrEmpty();
    }

    public Mono<MedicalSlot> findByMedicalAppointment(MedicalAppointment medicalAppointment) {
        return repository
                .findAll()
                .filter(slot -> slot.getMedicalAppointment().getId().equals(medicalAppointment.getId()))
                .single();
    }
}
