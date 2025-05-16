package com.api.v1.doctors.services;

import com.api.v1.common.ErrorMessages;
import com.api.v1.common.Result;
import com.api.v1.common.StatusCode;
import com.api.v1.doctors.controllers.DoctorController;
import com.api.v1.doctors.domain.DoctorRepository;
import com.api.v1.doctors.domain.exposed.Doctor;
import com.api.v1.doctors.response.DoctorResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class DoctorRetrievalServiceImpl implements DoctorRetrievalService {

    private final DoctorRepository doctorRepository;

    public DoctorRetrievalServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public ResponseEntity<Result<DoctorResponseDto>> findByLicenseNumber(String licenseNumber) {
        Optional<Doctor> optional = doctorRepository.findByLicenseNumber(licenseNumber);
        if (optional.isEmpty()) {
            Result<DoctorResponseDto> error = Result.error(ErrorMessages.DOCTOR_NOT_FOUND.value());
            return ResponseEntity.status(StatusCode.NOT_FOUND.code()).body(error);
        }
        Doctor doctor = optional.get();
        DoctorResponseDto responseDto = doctor
                .toDto()
                .add(linkTo(methodOn(DoctorController.class).findByLicenseNumber(licenseNumber)).withSelfRel());
        Result<DoctorResponseDto> success = Result.success(responseDto);
        return ResponseEntity.ok(success);
    }

    @Override
    public ResponseEntity<Result<Page<DoctorResponseDto>>> findAll(Pageable pageable) {
        Page<DoctorResponseDto> response = doctorRepository.findAll(pageable).map(Doctor::toDto);
        Result<Page<DoctorResponseDto>> success = Result.success(response);
        return ResponseEntity.ok(success);
    }
}
