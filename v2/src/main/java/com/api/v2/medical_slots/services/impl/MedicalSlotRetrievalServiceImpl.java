package com.api.v2.medical_slots.services.impl;

import com.api.v2.doctors.utils.DoctorFinderUtil;
import com.api.v2.medical_slots.controllers.MedicalSlotController;
import com.api.v2.medical_slots.domain.MedicalSlot;
import com.api.v2.medical_slots.domain.MedicalSlotRepository;
import com.api.v2.medical_slots.dtos.MedicalSlotResponseDto;
import com.api.v2.medical_slots.services.interfaces.MedicalSlotRetrievalService;
import com.api.v2.medical_slots.utils.MedicalSlotFinderUtil;
import com.api.v2.medical_slots.utils.MedicalSlotResponseMapper;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
    public Mono<MedicalSlotResponseDto> findById(String id) {
        return medicalSlotFinderUtil
                .findById(id)
                .flatMap(slot -> {
                    String medicalLicenseNumber = slot.getDoctor().getLicenseNumber();
                    EntityModel<MedicalSlot> entityModel = EntityModel.of(slot);
                    List<Link> links = new ArrayList<>();
                    links.add(linkTo(methodOn(MedicalSlotController.class)
                            .findAllByDoctor(medicalLicenseNumber))
                            .withRel("Get all medical slots by the medical license number")
                    );
                    entityModel.add(links);
                    return MedicalSlotResponseMapper.mapToMono(entityModel.getContent());
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
