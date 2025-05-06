package com.api.v1.medical_slots.services;

import com.api.v1.doctors.domain.exposed.Doctor;
import com.api.v1.doctors.utils.DoctorFinder;
import com.api.v1.medical_slots.domain.MedicalSlot;
import com.api.v1.medical_slots.domain.MedicalSlotRepository;
import com.api.v1.medical_slots.response.MedicalSlotResponseDto;
import com.api.v1.medical_slots.utils.MedicalSlotFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MedicalSlotRetrievalServiceImpl implements MedicalSlotRetrievalService {

    private final DoctorFinder doctorFinder;
    private final MedicalSlotFinder medicalSlotFinder;
    private final MedicalSlotRepository medicalSlotRepository;

    @Override
    public Mono<ResponseEntity<MedicalSlotResponseDto>> findByDoctorAndId(String doctorId, String medicalSlotId) {
        return doctorFinder
                .findById(doctorId)
                .zipWith(medicalSlotFinder.findById(medicalSlotId))
                .flatMap(tuple -> {
                    Doctor doctor = tuple.getT1();
                    MedicalSlot medicalSlot = tuple.getT2();
                    return medicalSlotRepository
                            .find(doctor, medicalSlot.getId())
                            .map(MedicalSlot::toDto)
                            .map(ResponseEntity::ok);
                });
    }

    @Override
    public ResponseEntity<Flux<MedicalSlotResponseDto>> findAllByDoctor(String doctorId) {
       var flux = doctorFinder
                .findById(doctorId)
                .flatMapMany(foundDoctor -> {
                    return medicalSlotRepository
                            .find(foundDoctor)
                            .map(MedicalSlot::toDto);
                });
       return ResponseEntity.ok(flux);
    }

    @Override
    public ResponseEntity<Flux<MedicalSlotResponseDto>> findAll() {
        var flux = medicalSlotRepository
                .findAll()
                .map(MedicalSlot::toDto);
        return ResponseEntity.ok(flux);
    }
}
