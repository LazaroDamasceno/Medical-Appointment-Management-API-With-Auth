package com.api.v1.doctors.services;

import com.api.v1.common.DuplicatedLicenseNumberException;
import com.api.v1.common.LicenseNumber;
import com.api.v1.doctors.domain.DoctorCrudRepository;
import com.api.v1.doctors.Doctor;
import com.api.v1.doctors.DoctorResponseDTO;
import com.api.v1.people.Person;
import com.api.v1.people.PersonRegistrationDTO;
import com.api.v1.people.exceptions.DuplicatedSINException;
import com.api.v1.people.PersonRegistrationService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class DoctorRegistrationServiceImpl implements DoctorRegistrationService {

    private final DoctorCrudRepository repository;
    private final PersonRegistrationService personRegistrationService;

    public DoctorRegistrationServiceImpl(DoctorCrudRepository repository,
                                         PersonRegistrationService personRegistrationService
    ) {
        this.repository = repository;
        this.personRegistrationService = personRegistrationService;
    }

    @Override
    public ResponseEntity<DoctorResponseDTO> register(@LicenseNumber String medicalLicenseNumber,
                                                      @Valid PersonRegistrationDTO registrationDTO
    ) {
        validate(medicalLicenseNumber, registrationDTO);
        Person savedPerson = personRegistrationService.register(registrationDTO);
        Doctor newDoctor = Doctor.of(savedPerson, medicalLicenseNumber);
        Doctor savedDoctor = repository.save(newDoctor);
        DoctorResponseDTO responseDto = savedDoctor.toDTO();
        return ResponseEntity
                .created(URI.create("/api/v1/doctors"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseDto);
    }

    private void validate(String medicalLicenseNumber, PersonRegistrationDTO registrationDTO) {
        if (repository.findByLicenseNumber(medicalLicenseNumber).isPresent()) {
            throw new DuplicatedLicenseNumberException();
        }
        if (repository.findBySIN(registrationDTO.sin()).isPresent()) {
            throw new DuplicatedSINException();
        }
        if (repository.findByEmail(registrationDTO.email()).isPresent()) {
            throw new DuplicatedSINException();
        }
    }
}
