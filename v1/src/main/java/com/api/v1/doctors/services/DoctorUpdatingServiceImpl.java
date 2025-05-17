package com.api.v1.doctors.services;

import com.api.v1.common.ObjectId;
import com.api.v1.doctors.domain.DoctorAuditTrail;
import com.api.v1.doctors.domain.DoctorAuditTrailRepository;
import com.api.v1.doctors.domain.DoctorRepository;
import com.api.v1.doctors.domain.exposed.Doctor;
import com.api.v1.doctors.utils.exposed.DoctorFinder;
import com.api.v1.people.domain.exposed.Person;
import com.api.v1.people.requests.PersonUpdatingDto;
import com.api.v1.people.services.exposed.PersonUpdatingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DoctorUpdatingServiceImpl implements DoctorUpdatingService {

    private final DoctorRepository repository;
    private final DoctorAuditTrailRepository auditTrailRepository;
    private final DoctorFinder finder;
    private final PersonUpdatingService personUpdatingService;

    public DoctorUpdatingServiceImpl(DoctorRepository repository,
                                     DoctorAuditTrailRepository auditTrailRepository,
                                     DoctorFinder finder,
                                     PersonUpdatingService personUpdatingService
    ) {
        this.repository = repository;
        this.auditTrailRepository = auditTrailRepository;
        this.finder = finder;
        this.personUpdatingService = personUpdatingService;
    }

    @Override
    public ResponseEntity<Void> update(@ObjectId String licenseNumber, @Valid PersonUpdatingDto personUpdatingDto) {
        Doctor foundDoctor = finder.findByLicenseNumber(licenseNumber);
        DoctorAuditTrail auditTrail = DoctorAuditTrail.of(foundDoctor);
        DoctorAuditTrail savedAuditTrail = auditTrailRepository.save(auditTrail);
        Person updatedPerson = personUpdatingService.update(foundDoctor.person(), personUpdatingDto);
        foundDoctor.update(updatedPerson);
        Doctor updatedDoctor = repository.save(foundDoctor);
        return ResponseEntity.noContent().build();
    }
}
