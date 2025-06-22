package com.api.v1.medical_slots.services;

import com.api.v1.common.DuplicatedBookingDateTimeException;
import com.api.v1.common.LicenseNumber;
import com.api.v1.doctors.Doctor;
import com.api.v1.doctors.DoctorFinder;
import com.api.v1.medical_slots.MedicalSlot;
import com.api.v1.medical_slots.DefaultMedicalSlotResponseDTO;
import com.api.v1.medical_slots.domain.MedicalSlotCrudRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MedicalSlotRegistrationServiceImpl implements MedicalSlotRegistrationService {

    private final DoctorFinder doctorFinder;
    private final MedicalSlotCrudRepository crudRepository;

    public MedicalSlotRegistrationServiceImpl(
            DoctorFinder doctorFinder,
            MedicalSlotCrudRepository crudRepository
    ) {
        this.doctorFinder = doctorFinder;
        this.crudRepository = crudRepository;
    }

    @Override
    public ResponseEntity<DefaultMedicalSlotResponseDTO> register(
            @LicenseNumber String medicalLicenseNumber,
            @NotNull LocalDateTime availableAt
    ) {
        Doctor foundDoctor = doctorFinder.findActiveByLicenseNumber(medicalLicenseNumber);
        Optional<MedicalSlot> foundSlot = crudRepository.findActiveByDoctorAndAvailable(foundDoctor.id(), availableAt);
        if (foundSlot.isPresent()) {
            throw new DuplicatedBookingDateTimeException();
        }
        MedicalSlot newSlot = MedicalSlot.of(foundDoctor, availableAt);
        MedicalSlot savedSlot = crudRepository.save(newSlot);
        DefaultMedicalSlotResponseDTO responseDTO = savedSlot.toDTO();
        return ResponseEntity
                .created(URI.create("/api/v1/medical-slots"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseDTO);
    }
}
