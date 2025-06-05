package com.api.v1.medical_slots.services;

import com.api.v1.common.LicenseNumber;
import com.api.v1.common.ObjectId;
import com.api.v1.doctors.Doctor;
import com.api.v1.doctors.DoctorFinder;
import com.api.v1.medical_slots.MedicalSlot;
import com.api.v1.medical_slots.MedicalSlotFinder;
import com.api.v1.medical_slots.MedicalSlotResponseDTO;
import com.api.v1.medical_slots.controllers.MedicalSlotController;
import com.api.v1.medical_slots.domain.MedicalSlotCrudRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class MedicalSlotRetrievalServiceImpl implements MedicalSlotRetrievalService {

    private final MedicalSlotCrudRepository crudRepository;
    private final DoctorFinder doctorFinder;
    private final MedicalSlotFinder medicalSlotFinder;

    public MedicalSlotRetrievalServiceImpl(MedicalSlotCrudRepository crudRepository,
                                           DoctorFinder doctorFinder,
                                           MedicalSlotFinder medicalSlotFinder
    ) {
        this.crudRepository = crudRepository;
        this.doctorFinder = doctorFinder;
        this.medicalSlotFinder = medicalSlotFinder;
    }

    @Override
    public ResponseEntity<MedicalSlotResponseDTO> findByIdAndDoctor(@ObjectId String id,
                                                                    @LicenseNumber String medicalLicenseNumber
    ) {
        Doctor foundDoctor = doctorFinder.findByLicenseNumber(medicalLicenseNumber);
        MedicalSlot foundSlot = medicalSlotFinder.findByIdAndDoctor(id, foundDoctor);
        MedicalSlotResponseDTO responseDTO = foundSlot.toDTO();
        responseDTO.add(
            linkTo(methodOn(MedicalSlotController.class).findByIdAndDoctor(id, medicalLicenseNumber)).withSelfRel()
        );
        return ResponseEntity.ok(responseDTO);
    }

    @Override
    public ResponseEntity<Page<MedicalSlotResponseDTO>> findAllByDoctor(@LicenseNumber String medicalLicenseNumber,
                                                                        Pageable pageable
    ) {
        Doctor foundDoctor = doctorFinder.findByLicenseNumber(medicalLicenseNumber);
        var all = crudRepository.findAllByDoctor(foundDoctor.getId(), pageable).map(MedicalSlot::toDTO);
        return ResponseEntity.ok(all);
    }

    @Override
    public ResponseEntity<Page<MedicalSlotResponseDTO>> findAll(Pageable pageable) {
        var all = crudRepository.findAll(pageable).map(MedicalSlot::toDTO);
        return ResponseEntity.ok(all);
    }
}
