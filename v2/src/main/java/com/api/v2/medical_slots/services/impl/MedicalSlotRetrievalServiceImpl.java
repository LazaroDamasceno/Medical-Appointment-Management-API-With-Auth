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
    public Mono<HalResourceWrapper<MedicalSlotResponseDto, Void>> findById(String slotId) {
        return medicalSlotFinderUtil
                .findById(slotId)
                .flatMap(slot -> {
                    return MedicalSlotResponseMapper
                            .mapToMono(slot)
                            .map(dto -> {
                                var medicalLicenseNumber = dto.doctor().medicalLicenseNumber();
                                return HalResourceWrapper
                                        .wrap(dto)
                                        .withLinks(
                                                linkTo(
                                                        MedicalSlotController.class,
                                                        controller -> controller.findById(slotId)
                                                ).withSelfRel(),
                                                linkTo(
                                                        MedicalSlotController.class,
                                                        controller -> controller.cancel(slotId)
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
                                            controller -> controller.findById(dto.id())
                                    ).withRel("find_medical_slot_by_id"),
                                    linkTo(
                                            MedicalSlotController.class,
                                            controller -> controller.findAllByDoctor(medicalLicenseNumber)
                                    ).withRel("find_medical_slots_by_doctor"),
                                    linkTo(
                                            MedicalSlotController.class,
                                            controller -> controller.findAllActiveByDoctor(medicalLicenseNumber)
                                    ).withRel("find_active_medical_slots_by_doctor"),
                                    linkTo(
                                            MedicalSlotController.class,
                                            controller -> controller.findAllCanceledByDoctor(medicalLicenseNumber)
                                    ).withRel("find_canceled_medical_slots_by_doctor"),
                                    linkTo(
                                            MedicalSlotController.class,
                                            controller -> controller.findAllCompletedByDoctor(medicalLicenseNumber)
                                    ).withRel("find_completed_medical_slots_by_doctor"),
                                    linkTo(
                                            MedicalSlotController.class,
                                            controller -> controller.cancel(dto.id())
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
                                            controller -> controller.findById(dto.id())
                                    ).withRel("find_medical_slot_by_id"),
                                    linkTo(
                                            MedicalSlotController.class,
                                            controller -> controller.findAllByDoctor(medicalLicenseNumber)
                                    ).withRel("find_medical_slots_by_doctor"),
                                    linkTo(
                                            MedicalSlotController.class,
                                            controller -> controller.findAllActiveByDoctor(medicalLicenseNumber)
                                    ).withRel("find_active_medical_slots_by_doctor"),
                                    linkTo(
                                            MedicalSlotController.class,
                                            controller -> controller.findAllCanceledByDoctor(medicalLicenseNumber)
                                    ).withRel("find_canceled_medical_slots_by_doctor"),
                                    linkTo(
                                            MedicalSlotController.class,
                                            controller -> controller.findAllCompletedByDoctor(medicalLicenseNumber)
                                    ).withRel("find_completed_medical_slots_by_doctor"),
                                    linkTo(
                                            MedicalSlotController.class,
                                            controller -> controller.cancel(dto.id())
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
                                            controller -> controller.findById(dto.id())
                                    ).withRel("find_medical_slot_by_id"),
                                    linkTo(
                                            MedicalSlotController.class,
                                            controller -> controller.findAllByDoctor(medicalLicenseNumber)
                                    ).withRel("find_medical_slots_by_doctor"),
                                    linkTo(
                                            MedicalSlotController.class,
                                            controller -> controller.findAllActiveByDoctor(medicalLicenseNumber)
                                    ).withRel("find_active_medical_slots_by_doctor"),
                                    linkTo(
                                            MedicalSlotController.class,
                                            controller -> controller.findAllCanceledByDoctor(medicalLicenseNumber)
                                    ).withRel("find_canceled_medical_slots_by_doctor"),
                                    linkTo(
                                            MedicalSlotController.class,
                                            controller -> controller.findAllCompletedByDoctor(medicalLicenseNumber)
                                    ).withRel("find_completed_medical_slots_by_doctor"),
                                    linkTo(
                                            MedicalSlotController.class,
                                            controller -> controller.cancel(dto.id())
                                    ).withRel("cancel_medical_slot_by_id")
                            );
                });
    }

    @Override
    public Flux<HalResourceWrapper<MedicalSlotResponseDto, Void>> findAll() {
        return medicalSlotRepository
                .findAll()
                .switchIfEmpty(Flux.empty())
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
                                            controller -> controller.findById(dto.id())
                                    ).withRel("find_medical_slot_by_id"),
                                    linkTo(
                                            MedicalSlotController.class,
                                            controller -> controller.findAllByDoctor(dto.doctor().medicalLicenseNumber())
                                    ).withRel("find_medical_slots_by_doctor"),
                                    linkTo(
                                            MedicalSlotController.class,
                                            controller -> controller.findAllActiveByDoctor(dto.doctor().medicalLicenseNumber())
                                    ).withRel("find_active_medical_slots_by_doctor"),
                                    linkTo(
                                            MedicalSlotController.class,
                                            controller -> controller.findAllCanceledByDoctor(dto.doctor().medicalLicenseNumber())
                                    ).withRel("find_canceled_medical_slots_by_doctor"),
                                    linkTo(
                                            MedicalSlotController.class,
                                            controller -> controller.findAllCompletedByDoctor(dto.doctor().medicalLicenseNumber())
                                    ).withRel("find_completed_medical_slots_by_doctor"),
                                    linkTo(
                                            MedicalSlotController.class,
                                            controller -> controller.cancel(dto.id())
                                    ).withRel("cancel_medical_slot_by_id")
                            );
                });
    }
}
