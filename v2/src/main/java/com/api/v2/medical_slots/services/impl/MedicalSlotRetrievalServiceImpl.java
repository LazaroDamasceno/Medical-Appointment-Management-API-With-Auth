package com.api.v2.medical_slots.services.impl;

import com.api.v2.doctors.utils.DoctorFinderUtil;
import com.api.v2.medical_slots.controllers.MedicalSlotController;
import com.api.v2.medical_slots.domain.MedicalSlot;
import com.api.v2.medical_slots.domain.MedicalSlotRepository;
import com.api.v2.medical_slots.dtos.MedicalSlotResponseDto;
import com.api.v2.medical_slots.services.interfaces.MedicalSlotRetrievalService;
import com.api.v2.medical_slots.utils.MedicalSlotFinderUtil;
import com.api.v2.medical_slots.utils.MedicalSlotResponseMapper;
import de.kamillionlabs.hateoflux.model.hal.HalResourceWrapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static de.kamillionlabs.hateoflux.linkbuilder.SpringControllerLinkBuilder.linkTo;

@Service
public class MedicalSlotRetrievalServiceImpl implements MedicalSlotRetrievalService {

    private final DoctorFinderUtil doctorFinderUtil;
    private final MedicalSlotRepository medicalSlotRepository;
    private final MedicalSlotFinderUtil medicalSlotFinderUtil;

    public MedicalSlotRetrievalServiceImpl(
            DoctorFinderUtil doctorFinderUtil,
            MedicalSlotRepository medicalSlotRepository,
            MedicalSlotFinderUtil medicalSlotFinderUtil
    ) {
        this.doctorFinderUtil = doctorFinderUtil;
        this.medicalSlotRepository = medicalSlotRepository;
        this.medicalSlotFinderUtil = medicalSlotFinderUtil;
    }

    @Override
    public Mono<HalResourceWrapper<MedicalSlotResponseDto, Void>> findById(String id) {
        return medicalSlotFinderUtil
                .findById(id)
                .flatMap(slot -> {
                    return MedicalSlotResponseMapper
                            .mapToMono(slot)
                            .map(dto -> {
                                var medicalLicenseNumber = dto.getDoctorResponseDto().medicalLicenseNumber();
                                return HalResourceWrapper
                                        .wrap(dto)
                                        .withLinks(
                                                linkTo(
                                                        MedicalSlotController.class,
                                                        controller -> controller.findById(id)
                                                ).withSelfRel(),
                                                linkTo(
                                                        MedicalSlotController.class,
                                                        controller -> controller.findAllByDoctor(medicalLicenseNumber)
                                                ).withRel("find all medical slots associated with doctor"),
                                                linkTo(
                                                        MedicalSlotController.class,
                                                        controller -> controller.cancel(id)
                                                ).withRel("cancel found medical slot")
                                        );
                            });
                });
    }

    @Override
    public Flux<MedicalSlotResponseDto> findAllByDoctor(String medicalLicenseNumber) {
        return doctorFinderUtil
                .findByLicenseNumber(medicalLicenseNumber)
                .flatMapMany(doctor -> {
                    return medicalSlotRepository
                            .findAll()
                            .filter(slot -> slot.getDoctor().getId().equals(doctor.getId()))
                            .flatMap(MedicalSlotResponseMapper::mapToMono);
                });
    }

    @Override
    public Flux<MedicalSlotResponseDto> findAll() {
        return medicalSlotRepository
                .findAll()
                .flatMap(MedicalSlotResponseMapper::mapToMono);
    }
}
