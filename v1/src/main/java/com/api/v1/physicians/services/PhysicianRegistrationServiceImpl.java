package com.api.v1.physicians.services;

import com.api.v1.Status201;
import com.api.v1.people.models.Person;
import com.api.v1.people.services.PersonRegistrationService;
import com.api.v1.physicians.exceptions.DuplicatedMedicalLicenseException;
import com.api.v1.physicians.helpers.MedicalLicense;
import com.api.v1.physicians.models.Physician;
import com.api.v1.physicians.repositories.PhysicianRepository;
import com.api.v1.physicians.requests.PhysicianRegistrationDto;
import com.api.v1.physicians.responses.PhysicianResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PhysicianRegistrationServiceImpl implements PhysicianRegistrationService {

    private final PhysicianRepository physicianRepository;
    private final PersonRegistrationService personRegistrationService;

    @Override
    public ResponseEntity<PhysicianResponseDto> register(@Valid PhysicianRegistrationDto dto) {
        isMedicalLicenseNumber(dto.licenseNumber());
        Person newPerson = personRegistrationService.register(dto.person());
        Physician newPhysician = Physician.of(dto, newPerson);
        Physician savedPhysician = physicianRepository.save(newPhysician);
        PhysicianResponseDto responseDto = savedPhysician.toDto();
        return ResponseEntity.status(Status201.get201()).body(responseDto);
    }

    private void isMedicalLicenseNumber(MedicalLicense medicalLicense) {
        if (physicianRepository
                .findByMedicalLicenseIdAndMedicalLicenseState(
                        medicalLicense.id(),
                        medicalLicense.state())
                .isPresent()
        ) {
            throw new DuplicatedMedicalLicenseException();
        }
    }
}
