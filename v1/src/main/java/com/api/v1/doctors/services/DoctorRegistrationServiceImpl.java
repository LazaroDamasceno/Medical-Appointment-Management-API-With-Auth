package com.api.v1.doctors.services;

import com.api.v1.common.ErrorMessages;
import com.api.v1.common.Result;
import com.api.v1.common.StatusCode;
import com.api.v1.doctors.domain.DoctorRepository;
import com.api.v1.doctors.domain.exposed.Doctor;
import com.api.v1.doctors.requests.DoctorRegistrationDto;
import com.api.v1.doctors.response.DoctorResponseDto;
import com.api.v1.people.domain.exposed.Person;
import com.api.v1.people.services.exposed.PersonRegistrationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Optional;

@Service
public class DoctorRegistrationServiceImpl implements DoctorRegistrationService {

    private final DoctorRepository doctorRepository;
    private final PersonRegistrationService personRegistrationService;

    public DoctorRegistrationServiceImpl(DoctorRepository doctorRepository,
                                         PersonRegistrationService personRegistrationService
    ) {
        this.doctorRepository = doctorRepository;
        this.personRegistrationService = personRegistrationService;
    }

    @Override
    public ResponseEntity<Result<DoctorResponseDto>> register(@Valid DoctorRegistrationDto registrationDto) {
        Optional<Doctor> foundDoctorBySIN = doctorRepository.findBySIN(registrationDto.person().sin());
        if (foundDoctorBySIN.isPresent()) {
            Result<DoctorResponseDto> error = Result.error(ErrorMessages.DUPLICATED_SIN.value());
            return ResponseEntity.status(StatusCode.CONFLICT.code()).body(error);
        }
        Optional<Doctor> foundDoctorByEmail = doctorRepository.findByEmail(registrationDto.person().email());
        if (foundDoctorByEmail.isPresent()) {
            Result<DoctorResponseDto> error = Result.error(ErrorMessages.DUPLICATED_EMAIL.value());
            return ResponseEntity.status(StatusCode.CONFLICT.code()).body(error);
        }
        Optional<Doctor> foundDoctorByLicenseNumber = doctorRepository.findByLicenseNumber(registrationDto.licenseNumber());
        if (foundDoctorByLicenseNumber.isPresent()) {
            Result<DoctorResponseDto> error = Result.error(ErrorMessages.DUPLICATED_LICENSE_NUMBER.value());
            return ResponseEntity.status(StatusCode.CONFLICT.code()).body(error);
        }
        Person savedPerson = personRegistrationService.register(registrationDto.person());
        Doctor newDoctor = Doctor.of(savedPerson, registrationDto.licenseNumber());
        Doctor savedDoctor = doctorRepository.save(newDoctor);
        DoctorResponseDto responseDto = savedDoctor.toDto();
        Result<DoctorResponseDto> success = Result.created(responseDto);
        return ResponseEntity.created(URI.create("/api/v1/doctors")).body(success);
    }
}
