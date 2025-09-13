package com.api.v1;

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
        return ResponseEntity.status(Constant.CREATED).body(responseDto);
    }

    private void isMedicalLicenseNumber(MedicalLicense medicalLicense) {
        if (physicianRepository.findByMedicalLicense(medicalLicense).isPresent()) {
            throw new DuplicatedMedicalLicenseException();
        }
    }
}
