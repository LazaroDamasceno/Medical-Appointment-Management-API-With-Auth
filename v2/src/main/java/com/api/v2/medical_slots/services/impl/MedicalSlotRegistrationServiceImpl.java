package com.api.v2.medical_slots.services.impl;

import com.api.v2.common.BlockedDateTimeHandler;
import com.api.v2.doctors.domain.Doctor;
import com.api.v2.doctors.utils.DoctorFinder;
import com.api.v2.medical_slots.controllers.MedicalSlotController;
import com.api.v2.medical_slots.domain.MedicalSlot;
import com.api.v2.medical_slots.domain.MedicalSlotRepository;
import com.api.v2.medical_slots.dtos.MedicalSlotRegistrationDto;
import com.api.v2.medical_slots.dtos.MedicalSlotResponseDto;
import com.api.v2.medical_slots.exceptions.UnavailableMedicalSlotException;
import com.api.v2.medical_slots.services.interfaces.MedicalSlotRegistrationService;
import com.api.v2.medical_slots.utils.MedicalSlotFinder;
import com.api.v2.medical_slots.utils.MedicalSlotResponseMapper;
import de.kamillionlabs.hateoflux.model.hal.HalResourceWrapper;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import static de.kamillionlabs.hateoflux.linkbuilder.SpringControllerLinkBuilder.linkTo;

@Service
public class MedicalSlotRegistrationServiceImpl implements MedicalSlotRegistrationService {

    private final MedicalSlotRepository medicalSlotRepository;
    private final DoctorFinder doctorFinder;
    private final MedicalSlotFinder medicalSlotFinder;

    public MedicalSlotRegistrationServiceImpl(
            MedicalSlotRepository medicalSlotRepository,
            DoctorFinder doctorFinder,
            MedicalSlotFinder medicalSlotFinder
    ) {
        this.medicalSlotRepository = medicalSlotRepository;
        this.doctorFinder = doctorFinder;
        this.medicalSlotFinder = medicalSlotFinder;
    }

    @Override
    public Mono<HalResourceWrapper<MedicalSlotResponseDto, Void>> register(@Valid MedicalSlotRegistrationDto registrationDto) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZoneOffset zoneOffset = OffsetDateTime
                .ofInstant(registrationDto.availableAt().toInstant(ZoneOffset.UTC), ZoneId.systemDefault())
                .getOffset();
        return doctorFinder
                .findByLicenseNumber(registrationDto.medicalLicenseNumber())
                .flatMap(doctor -> {
                    return BlockedDateTimeHandler
                            .handle(registrationDto.availableAt().toLocalDate())
                            .then(Mono.defer(() ->
                                    onUnavailableMedicalSlot(doctor, registrationDto.availableAt(), zoneId, zoneOffset)
                                            .then(Mono.defer(() -> {
                                                MedicalSlot medicalSlot = MedicalSlot.of(doctor, registrationDto.availableAt(), zoneId, zoneOffset);
                                                String id = medicalSlot.getId();
                                                String medicalLicenseNumber = medicalSlot.getDoctor().getMedicalLicenseNumber();
                                                return medicalSlotRepository
                                                        .save(medicalSlot)
                                                        .flatMap(savedSlot ->
                                                                MedicalSlotResponseMapper
                                                                        .mapToMono(savedSlot)
                                                                        .map(dto -> HalResourceWrapper
                                                                                .wrap(dto)
                                                                                .withLinks(
                                                                                        linkTo(
                                                                                                MedicalSlotController.class,
                                                                                                controller -> controller.cancel(registrationDto.medicalLicenseNumber(), id)
                                                                                        ).withRel("cancel_just_registered_medical_slot"),
                                                                                        linkTo(
                                                                                                MedicalSlotController.class,
                                                                                                controller -> controller.findAllByDoctor(medicalLicenseNumber)
                                                                                        ).withRel("find_all_medical_slots_by_doctor")
                                                                                )
                                                                        )
                                                        );
                                            }))
                            ));
                });
    }

    private Mono<Void> onUnavailableMedicalSlot(Doctor doctor,
                                                LocalDateTime availableAt,
                                                ZoneId availableAtZoneId,
                                                ZoneOffset availableAtZoneOffset
    ) {
        return medicalSlotFinder
                .findActiveByDoctorAndAvailableAt(doctor, availableAt, availableAtZoneId, availableAtZoneOffset)
                .hasElement()
                .flatMap(exists -> {
                    if (exists) {
                        String message = "The given datetime %s is already registered for another medical slot".formatted(availableAt);
                        return Mono.error(new UnavailableMedicalSlotException(message));
                    }
                    return Mono.empty();
                });
    }
}
