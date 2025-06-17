package com.api.v1.medical_slots.services;

import com.api.v1.common.LicenseNumber;
import com.api.v1.common.ObjectId;
import com.api.v1.doctors.Doctor;
import com.api.v1.doctors.DoctorFinder;
import com.api.v1.medical_slots.MedicalSlot;
import com.api.v1.medical_slots.MedicalSlotFinder;
import com.api.v1.medical_slots.domain.MedicalSlotAuditRepository;
import com.api.v1.medical_slots.domain.MedicalSlotAuditTrail;
import com.api.v1.medical_slots.domain.MedicalSlotCrudRepository;
import com.api.v1.medical_slots.enums.MedicalSlotStatus;
import com.api.v1.medical_slots.exceptions.CancelledMedicalSlotException;
import com.api.v1.medical_slots.exceptions.CompletedMedicalSlotException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MedicalSlotManagementServiceImpl implements MedicalSlotManagementService {

    private final MedicalSlotFinder slotFinder;
    private final DoctorFinder doctorFinder;
    private final MedicalSlotCrudRepository crudRepository;
    private final MedicalSlotAuditRepository auditRepository;

    public MedicalSlotManagementServiceImpl(
            MedicalSlotFinder slotFinder,
            DoctorFinder doctorFinder,
            MedicalSlotCrudRepository crudRepository,
            MedicalSlotAuditRepository auditRepository
    ) {
        this.slotFinder = slotFinder;
        this.doctorFinder = doctorFinder;
        this.crudRepository = crudRepository;
        this.auditRepository = auditRepository;
    }

    @Override
    public ResponseEntity<Void> cancel(
            @LicenseNumber String medicalLicenseNumber,
            @ObjectId String id
    ) {
        Doctor foundDoctor = doctorFinder.findByLicenseNumber(medicalLicenseNumber);
        MedicalSlot foundSlot = slotFinder.findByIdAndDoctor(id, foundDoctor);
        validate(foundSlot);
        MedicalSlotAuditTrail auditTrail = MedicalSlotAuditTrail.of(foundSlot);
        MedicalSlotAuditTrail savedAuditTrail = auditRepository.save(auditTrail);
        MedicalSlot updatedSlot = foundSlot.markAsCancelled();
        MedicalSlot savedSlot = crudRepository.save(updatedSlot);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> complete(
            @LicenseNumber String medicalLicenseNumber,
            @ObjectId String id
    ) {
        Doctor foundDoctor = doctorFinder.findByLicenseNumber(medicalLicenseNumber);
        MedicalSlot foundSlot = slotFinder.findByIdAndDoctor(id, foundDoctor);
        validate(foundSlot);
        MedicalSlotAuditTrail auditTrail = MedicalSlotAuditTrail.of(foundSlot);
        MedicalSlotAuditTrail savedAuditTrail = auditRepository.save(auditTrail);
        MedicalSlot updatedSlot = foundSlot.markAsCancelled();
        MedicalSlot savedSlot = crudRepository.save(updatedSlot);
        return ResponseEntity.noContent().build();
    }

    private void validate(MedicalSlot medicalSlot) {
        if (medicalSlot.status().equals(MedicalSlotStatus.CANCELLED)) {
            throw new CancelledMedicalSlotException(medicalSlot.id());
        }
        if (medicalSlot.status().equals(MedicalSlotStatus.COMPLETED)) {
            throw new CompletedMedicalSlotException(medicalSlot.id());
        }
    }
}
