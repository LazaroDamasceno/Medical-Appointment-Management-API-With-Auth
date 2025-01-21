package com.api.v2.medical_slots.services.impl;

import com.api.v2.doctors.utils.DoctorFinderUtil;
import com.api.v2.medical_slots.controllers.MedicalSlotController;
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
                                var medicalLicenseNumber = dto.getDoctor().medicalLicenseNumber();
                                return HalResourceWrapper
                                        .wrap(dto)
                                        .withLinks(
                                                linkTo(
                                                        MedicalSlotController.class,
                                                        controller -> controller.findById(id)
                                                ).withSelfRel(),
                                                linkTo(
                                                        MedicalSlotController.class,
                                                        controller -> controller.cancel(id)
                                                ).withRel("cancel_found_medical_slot"),
                                                linkTo(
                                                        MedicalSlotController.class,
                                                        controller -> controller.findAllByDoctor(medicalLicenseNumber)
                                                ).withRel("find medical slots by doctor"),
                                                linkTo(
                                                        MedicalSlotController.class,
                                                        controller -> controller.findAllActiveByDoctor(medicalLicenseNumber)
                                                ).withRel("find active medical slots by doctor"),
                                                linkTo(
                                                        MedicalSlotController.class,
                                                        controller -> controller.findAllCanceledByDoctor(medicalLicenseNumber)
                                                ).withRel("find canceled medical slots by doctor"),
                                                linkTo(
                                                        MedicalSlotController.class,
                                                        controller -> controller.findAllCompletedByDoctor(medicalLicenseNumber)
                                                ).withRel("find completed medical slots by doctor")
                                        );
                            });
                });
    }

    @Override
    public Flux<HalResourceWrapper<MedicalSlotResponseDto, Void>> findAllActiveByDoctor(String medicalLicenseNumber) {
        return doctorFinderUtil
                .findByLicenseNumber(medicalLicenseNumber)
                .flatMapMany(doctor -> {
                    return medicalSlotRepository
                            .findAll()
                            .filter(slot -> slot.getDoctor().getId().equals(doctor.getId())
                                    && slot.getCanceledAt() == null
                                    && slot.getCompletedAt() == null
                            )
                            .switchIfEmpty(Flux.empty())
                            .flatMap(MedicalSlotResponseMapper::mapToMono);
                })
                .map(dto -> {
                    return HalResourceWrapper
                            .wrap(dto)
                            .withLinks(
                                    linkTo(
                                            MedicalSlotController.class,
                                            controller -> controller.findById(dto.getId())
                                    ).withRel("find_medical_slot_by_id"),
                                    linkTo(
                                            MedicalSlotController.class,
                                            controller -> controller.findAllByDoctor(medicalLicenseNumber)
                                    ).withRel("find medical slots by doctor"),
                                    linkTo(
                                            MedicalSlotController.class,
                                            controller -> controller.findAllActiveByDoctor(medicalLicenseNumber)
                                    ).withRel("find active medical slots by doctor"),
                                    linkTo(
                                            MedicalSlotController.class,
                                            controller -> controller.findAllCanceledByDoctor(medicalLicenseNumber)
                                    ).withRel("find canceled medical slots by doctor"),
                                    linkTo(
                                            MedicalSlotController.class,
                                            controller -> controller.findAllCompletedByDoctor(medicalLicenseNumber)
                                    ).withRel("find completed medical slots by doctor"),
                                    linkTo(
                                            MedicalSlotController.class,
                                            controller -> controller.cancel(dto.getId())
                                    ).withRel("cancel_medical_slot_by_id")
                            );
                });
    }

    @Override
    public Flux<HalResourceWrapper<MedicalSlotResponseDto, Void>> findAllCanceledByDoctor(String medicalLicenseNumber) {
        return doctorFinderUtil
                .findByLicenseNumber(medicalLicenseNumber)
                .flatMapMany(doctor -> {
                    return medicalSlotRepository
                            .findAll()
                            .filter(slot -> slot.getDoctor().getId().equals(doctor.getId())
                                    && slot.getCanceledAt() != null
                                    && slot.getCompletedAt() == null
                            )
                            .switchIfEmpty(Flux.empty())
                            .flatMap(MedicalSlotResponseMapper::mapToMono);
                })
                .map(dto -> {
                    return HalResourceWrapper
                            .wrap(dto)
                            .withLinks(
                                    linkTo(
                                            MedicalSlotController.class,
                                            controller -> controller.findById(dto.getId())
                                    ).withRel("find_medical_slot_by_id"),
                                    linkTo(
                                            MedicalSlotController.class,
                                            controller -> controller.findAllByDoctor(medicalLicenseNumber)
                                    ).withRel("find medical slots by doctor"),
                                    linkTo(
                                            MedicalSlotController.class,
                                            controller -> controller.findAllActiveByDoctor(medicalLicenseNumber)
                                    ).withRel("find active medical slots by doctor"),
                                    linkTo(
                                            MedicalSlotController.class,
                                            controller -> controller.findAllCanceledByDoctor(medicalLicenseNumber)
                                    ).withRel("find canceled medical slots by doctor"),
                                    linkTo(
                                            MedicalSlotController.class,
                                            controller -> controller.findAllCompletedByDoctor(medicalLicenseNumber)
                                    ).withRel("find completed medical slots by doctor"),
                                    linkTo(
                                            MedicalSlotController.class,
                                            controller -> controller.cancel(dto.getId())
                                    ).withRel("cancel_medical_slot_by_id")
                            );
                });
    }

    @Override
    public Flux<HalResourceWrapper<MedicalSlotResponseDto, Void>> findAllCompletedByDoctor(String medicalLicenseNumber) {
        return doctorFinderUtil
                .findByLicenseNumber(medicalLicenseNumber)
                .flatMapMany(doctor -> {
                    return medicalSlotRepository
                            .findAll()
                            .filter(slot -> slot.getDoctor().getId().equals(doctor.getId())
                                    && slot.getCanceledAt() == null
                                    && slot.getCompletedAt() != null
                            )
                            .switchIfEmpty(Flux.empty())
                            .flatMap(MedicalSlotResponseMapper::mapToMono);
                })
                .map(dto -> {
                    return HalResourceWrapper
                            .wrap(dto)
                            .withLinks(
                                    linkTo(
                                            MedicalSlotController.class,
                                            controller -> controller.findById(dto.getId())
                                    ).withRel("find_medical_slot_by_id"),
                                    linkTo(
                                            MedicalSlotController.class,
                                            controller -> controller.findAllByDoctor(medicalLicenseNumber)
                                    ).withRel("find medical slots by doctor"),
                                    linkTo(
                                            MedicalSlotController.class,
                                            controller -> controller.findAllActiveByDoctor(medicalLicenseNumber)
                                    ).withRel("find active medical slots by doctor"),
                                    linkTo(
                                            MedicalSlotController.class,
                                            controller -> controller.findAllCanceledByDoctor(medicalLicenseNumber)
                                    ).withRel("find canceled medical slots by doctor"),
                                    linkTo(
                                            MedicalSlotController.class,
                                            controller -> controller.findAllCompletedByDoctor(medicalLicenseNumber)
                                    ).withRel("find completed medical slots by doctor"),
                                    linkTo(
                                            MedicalSlotController.class,
                                            controller -> controller.cancel(dto.getId())
                                    ).withRel("cancel_medical_slot_by_id")
                            );
                });
    }

    @Override
    public Flux<HalResourceWrapper<MedicalSlotResponseDto, Void>> findAll() {
        return medicalSlotRepository
                .findAll()
                .flatMap(MedicalSlotResponseMapper::mapToMono)
                .map(dto -> {
                    return HalResourceWrapper
                            .wrap(dto)
                            .withLinks(
                                    linkTo(
                                            MedicalSlotController.class,
                                            MedicalSlotController::findAll
                                    ).withSelfRel(),
                                    linkTo(
                                            MedicalSlotController.class,
                                            controller -> controller.findById(dto.getId())
                                    ).withRel("find_medical_slot_by_id"),
                                    linkTo(
                                            MedicalSlotController.class,
                                            controller -> controller.findAllByDoctor(dto.getDoctor().medicalLicenseNumber())
                                    ).withRel("find medical slots by doctor"),
                                    linkTo(
                                            MedicalSlotController.class,
                                            controller -> controller.findAllActiveByDoctor(dto.getDoctor().medicalLicenseNumber())
                                    ).withRel("find active medical slots by doctor"),
                                    linkTo(
                                            MedicalSlotController.class,
                                            controller -> controller.findAllCanceledByDoctor(dto.getDoctor().medicalLicenseNumber())
                                    ).withRel("find canceled medical slots by doctor"),
                                    linkTo(
                                            MedicalSlotController.class,
                                            controller -> controller.findAllCompletedByDoctor(dto.getDoctor().medicalLicenseNumber())
                                    ).withRel("find completed medical slots by doctor"),
                                    linkTo(
                                            MedicalSlotController.class,
                                            controller -> controller.cancel(dto.getId())
                                    ).withRel("cancel_medical_slot_by_id")
                            );
                });
    }
}
