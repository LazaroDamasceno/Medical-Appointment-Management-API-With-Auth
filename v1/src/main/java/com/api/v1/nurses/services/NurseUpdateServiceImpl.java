package com.api.v1.nurses.services;

import com.api.v1.common.LicenseNumber;
import com.api.v1.nurses.domain.NurseAuditRepository;
import com.api.v1.nurses.domain.NurseAuditTrail;
import com.api.v1.nurses.domain.NurseCrudRepository;
import com.api.v1.nurses.domain.exposed.Nurse;
import com.api.v1.nurses.utils.exposed.NurseFinder;
import com.api.v1.people.domain.exposed.Person;
import com.api.v1.people.requests.PersonUpdateDTO;
import com.api.v1.people.services.exposed.PersonUpdateService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class NurseUpdateServiceImpl implements NurseUpdateService {

    private final NurseCrudRepository crudRepository;
    private final NurseAuditRepository auditRepository;
    private final NurseFinder nurseFinder;
    private final PersonUpdateService personUpdateService;

    public NurseUpdateServiceImpl(NurseCrudRepository crudRepository,
                                  NurseAuditRepository auditRepository,
                                  NurseFinder nurseFinder,
                                  PersonUpdateService personUpdateService
    ) {
        this.crudRepository = crudRepository;
        this.auditRepository = auditRepository;
        this.nurseFinder = nurseFinder;
        this.personUpdateService = personUpdateService;
    }

    @Override
    public ResponseEntity<Void> update(@LicenseNumber String licenseNumber, @Valid PersonUpdateDTO updateDTO) {
        Nurse foundNurse = nurseFinder.findByLicenseNumber(licenseNumber);
        NurseAuditTrail auditTrail = NurseAuditTrail.of(foundNurse);
        NurseAuditTrail savedAuditTrail = auditRepository.save(auditTrail);
        Person updatedPerson = personUpdateService.update(foundNurse.getPerson(), updateDTO);
        foundNurse.update(updatedPerson);
        Nurse updatedNurse = crudRepository.save(foundNurse);
        return ResponseEntity.noContent().build();
    }
}
