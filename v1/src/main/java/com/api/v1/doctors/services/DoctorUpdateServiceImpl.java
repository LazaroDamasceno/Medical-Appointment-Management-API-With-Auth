package com.api.v1.doctors.services;

import com.api.v1.common.ObjectId;
import com.api.v1.doctors.domain.DoctorAuditTrail;
import com.api.v1.doctors.domain.DoctorAuditRepository;
import com.api.v1.doctors.domain.DoctorCrudRepository;
import com.api.v1.doctors.domain.exposed.Doctor;
import com.api.v1.doctors.utils.exposed.DoctorFinder;
import com.api.v1.people.domain.exposed.Person;
import com.api.v1.people.requests.PersonUpdatingDTO;
import com.api.v1.people.services.exposed.PersonUpdateService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DoctorUpdateServiceImpl implements DoctorUpdateService {

    private final DoctorCrudRepository repository;
    private final DoctorAuditRepository auditTrailRepository;
    private final DoctorFinder finder;
    private final PersonUpdateService personUpdateService;

    public DoctorUpdateServiceImpl(DoctorCrudRepository repository,
                                   DoctorAuditRepository auditTrailRepository,
                                   DoctorFinder finder,
                                   PersonUpdateService personUpdateService
    ) {
        this.repository = repository;
        this.auditTrailRepository = auditTrailRepository;
        this.finder = finder;
        this.personUpdateService = personUpdateService;
    }

    @Override
    public ResponseEntity<Void> update(@ObjectId String licenseNumber, @Valid PersonUpdatingDTO personUpdatingDTO) {
        Doctor foundDoctor = finder.findByLicenseNumber(licenseNumber);
        DoctorAuditTrail auditTrail = DoctorAuditTrail.of(foundDoctor);
        DoctorAuditTrail savedAuditTrail = auditTrailRepository.save(auditTrail);
        Person updatedPerson = personUpdateService.update(foundDoctor.getPerson(), personUpdatingDTO);
        foundDoctor.update(updatedPerson);
        Doctor updatedDoctor = repository.save(foundDoctor);
        return ResponseEntity.noContent().build();
    }
}
