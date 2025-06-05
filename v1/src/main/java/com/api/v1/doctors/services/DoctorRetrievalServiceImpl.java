package com.api.v1.doctors.services;

import com.api.v1.common.LicenseNumber;
import com.api.v1.doctors.controllers.DoctorController;
import com.api.v1.doctors.domain.DoctorCrudRepository;
import com.api.v1.doctors.Doctor;
import com.api.v1.doctors.DoctorResponseDTO;
import com.api.v1.doctors.DoctorFinder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class DoctorRetrievalServiceImpl implements DoctorRetrievalService {

    private final DoctorFinder finder;
    private final DoctorCrudRepository repository;

    public DoctorRetrievalServiceImpl(DoctorFinder finder, DoctorCrudRepository repository) {
        this.finder = finder;
        this.repository = repository;
    }

    @Override
    public ResponseEntity<DoctorResponseDTO> findByLicenseNumber(@LicenseNumber String licenseNumber) {
        Doctor foundDoctor = finder.findByLicenseNumber(licenseNumber);
        DoctorResponseDTO responseDto = foundDoctor.toDto();
        responseDto.add(
                linkTo(methodOn(DoctorController.class).findByLicenseNumber(licenseNumber)).withSelfRel()
        );
        return ResponseEntity.ok(responseDto);
    }

    @Override
    public ResponseEntity<Page<DoctorResponseDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(repository.findAll(pageable).map(Doctor::toDto));
    }
}
