package com.api.v1.doctors.services;

import com.api.v1.common.LicenseNumber;
import com.api.v1.common.ObjectId;
import com.api.v1.common.ProfessionalStatus;
import com.api.v1.doctors.domain.DoctorAuditTrail;
import com.api.v1.doctors.domain.DoctorAuditRepository;
import com.api.v1.doctors.domain.DoctorCrudRepository;
import com.api.v1.doctors.Doctor;
import com.api.v1.doctors.exceptions.TerminatedDoctorException;
import com.api.v1.doctors.DoctorFinder;
import com.api.v1.people.Person;
import com.api.v1.people.PersonUpdateDTO;
import com.api.v1.people.PersonUpdateService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DoctorManagementServiceImpl implements DoctorManagementService {

    private final DoctorCrudRepository repository;
    private final DoctorAuditRepository auditTrailRepository;
    private final DoctorFinder finder;
    private final PersonUpdateService personUpdateService;

    public DoctorManagementServiceImpl(DoctorCrudRepository repository,
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
    public ResponseEntity<Void> terminate(@LicenseNumber String licenseNumber) {
        Doctor foundDoctor = finder.findByLicenseNumber(licenseNumber);
        onCurrentlyTerminatedDoctor(foundDoctor);
        DoctorAuditTrail auditTrail = DoctorAuditTrail.of(foundDoctor);
        DoctorAuditTrail savedAuditTrail = auditTrailRepository.save(auditTrail);
        foundDoctor.markedAsTerminated();
        Doctor updatedDoctor = repository.save(foundDoctor);
        return ResponseEntity.noContent().build();
    }

    private void onCurrentlyTerminatedDoctor(Doctor doctor) {
        if (doctor.getStatus().equals(ProfessionalStatus.TERMINATED)) {
            throw new TerminatedDoctorException(doctor.getLicenseNumber());
        }
    }

    @Override
    public ResponseEntity<Void> rehire(@LicenseNumber String licenseNumber) {
        Doctor foundDoctor = finder.findByLicenseNumber(licenseNumber);
        onCurrentlyActiveDoctor(foundDoctor);
        DoctorAuditTrail auditTrail = DoctorAuditTrail.of(foundDoctor);
        DoctorAuditTrail savedAuditTrail = auditTrailRepository.save(auditTrail);
        foundDoctor.markedAsRehired();
        Doctor updatedDoctor = repository.save(foundDoctor);
        return ResponseEntity.noContent().build();
    }

    private void onCurrentlyActiveDoctor(Doctor doctor) {
        if (doctor.getStatus().equals(ProfessionalStatus.ACTIVE)) {
            throw new TerminatedDoctorException(doctor.getLicenseNumber());
        }
    }

    @Override
    public ResponseEntity<Void> update(@ObjectId String licenseNumber, @Valid PersonUpdateDTO personUpdateDTO) {
        Doctor foundDoctor = finder.findByLicenseNumber(licenseNumber);
        DoctorAuditTrail auditTrail = DoctorAuditTrail.of(foundDoctor);
        DoctorAuditTrail savedAuditTrail = auditTrailRepository.save(auditTrail);
        Person updatedPerson = personUpdateService.update(foundDoctor.getPerson(), personUpdateDTO);
        foundDoctor.update(updatedPerson);
        Doctor updatedDoctor = repository.save(foundDoctor);
        return ResponseEntity.noContent().build();
    }
}
