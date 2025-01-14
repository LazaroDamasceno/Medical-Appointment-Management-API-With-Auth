package com.api.v2.medical_slots.services.impl;

import com.api.v2.doctors.domain.Doctor;
import com.api.v2.doctors.utils.DoctorFinderUtil;
import com.api.v2.medical_slots.domain.MedicalSlot;
import com.api.v2.medical_slots.domain.MedicalSlotRepository;
import com.api.v2.medical_slots.dtos.MedicalSlotRegistrationDto;
import com.api.v2.medical_slots.dtos.MedicalSlotResponseDto;
import com.api.v2.medical_slots.exceptions.UnavailableMedicalSlotException;
import com.api.v2.medical_slots.services.interfaces.MedicalSlotRegistrationService;
import com.api.v2.medical_slots.utils.MedicalSlotFinderUtil;
import com.api.v2.medical_slots.utils.MedicalSlotResponseMapper;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class MedicalSlotRegistrationServiceImpl implements MedicalSlotRegistrationService {

    private final MedicalSlotRepository medicalSlotRepository;
    private final DoctorFinderUtil doctorFinderUtil;
    private final MedicalSlotFinderUtil medicalSlotFinderUtil;

    public MedicalSlotRegistrationServiceImpl(
            MedicalSlotRepository medicalSlotRepository,
            DoctorFinderUtil doctorFinderUtil,
            MedicalSlotFinderUtil medicalSlotFinderUtil
    ) {
        this.medicalSlotRepository = medicalSlotRepository;
        this.doctorFinderUtil = doctorFinderUtil;
        this.medicalSlotFinderUtil = medicalSlotFinderUtil;
    }

    @Override
    public Mono<MedicalSlotResponseDto> register(@Valid MedicalSlotRegistrationDto registrationDto) {
        return doctorFinderUtil
                .findByLicenseNumber(registrationDto.medicalLicenseNumber())
                .flatMap(doctor -> {
                    return onUnavailableMedicalSlot(doctor, registrationDto.availableAt())
                            .then(Mono.defer(() -> {
                                MedicalSlot medicalSlot = MedicalSlot.create(doctor, registrationDto.availableAt());
                                return medicalSlotRepository
                                        .save(medicalSlot)
                                        .then(MedicalSlotResponseMapper.mapToMono(medicalSlot));
                            }));
                });
    }

    private Mono<Void> onUnavailableMedicalSlot(Doctor doctor, LocalDateTime availableAt) {
        return medicalSlotFinderUtil
                .findActiveByDoctorAndAvailableAt(doctor, availableAt)
                .hasElement()
                .flatMap(exists -> {
                    System.out.println(exists);
                    if (exists) {
                        String message = "The given datetime %s is already registered for another medical slot".formatted(availableAt);
                        return Mono.error(new UnavailableMedicalSlotException(message));
                    }
                    return Mono.empty();
                });
    }
}
