package com.api.v1.doctors.services;

import com.api.v1.common.ErrorMessages;
import com.api.v1.common.LicenseNumber;
import com.api.v1.common.Result;
import com.api.v1.common.StatusCode;
import com.api.v1.doctors.domain.DoctorAuditTrail;
import com.api.v1.doctors.domain.DoctorAuditTrailRepository;
import com.api.v1.doctors.domain.DoctorRepository;
import com.api.v1.doctors.domain.exposed.Doctor;
import com.api.v1.doctors.response.DoctorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DoctorManagementServiceImpl implements DoctorManagementService {

    private final DoctorRepository doctorRepository;
    private final DoctorAuditTrailRepository auditTrailRepository;

    public DoctorManagementServiceImpl(DoctorRepository doctorRepository,
                                       DoctorAuditTrailRepository auditTrailRepository
    ) {
        this.doctorRepository = doctorRepository;
        this.auditTrailRepository = auditTrailRepository;
    }

    @Override
    public ResponseEntity<Result<DoctorResponseDto>> terminate(@LicenseNumber String licenseNumber) {
        Optional<Doctor> optionalActiveDoctor = doctorRepository.findActiveByLicenseNumber(licenseNumber);
        if (optionalActiveDoctor.isEmpty()) {
            return ResponseEntity.status(StatusCode.NOT_FOUND.code()).body(onNotFoundDoctor());
        }
        if (doctorRepository.findTerminatedByLicenseNumber(licenseNumber).isEmpty()) {
            return ResponseEntity.status(StatusCode.CONFLICT.code()).body(onTerminatedDoctor());
        }
        Doctor activeDoctor = optionalActiveDoctor.get();
        DoctorAuditTrail auditTrail = DoctorAuditTrail.of(activeDoctor);
        DoctorAuditTrail savedAuditTrail =auditTrailRepository.save(auditTrail);
        activeDoctor.markedAsTerminated();
        Doctor updatedDoctor = doctorRepository.save(activeDoctor);
        DoctorResponseDto responseDto = updatedDoctor.toDto();
        Result<DoctorResponseDto> success = Result.success(responseDto);
        return ResponseEntity.ok(success);
    }

    @Override
    public ResponseEntity<Result<DoctorResponseDto>> rehire(@LicenseNumber String licenseNumber) {
        Optional<Doctor> optionalTerminatedDoctor = doctorRepository.findTerminatedByLicenseNumber(licenseNumber);
        if (optionalTerminatedDoctor.isEmpty()) {
            return ResponseEntity.status(StatusCode.NOT_FOUND.code()).body(onNotFoundDoctor());
        }
        if (doctorRepository.findActiveByLicenseNumber(licenseNumber).isEmpty()) {
            return ResponseEntity.status(StatusCode.CONFLICT.code()).body(onActiveDoctor());
        }
        Doctor terminatedDoctor = optionalTerminatedDoctor.get();
        DoctorAuditTrail auditTrail = DoctorAuditTrail.of(terminatedDoctor);
        DoctorAuditTrail savedAuditTrail =auditTrailRepository.save(auditTrail);
        terminatedDoctor.markedAsRehired();
        Doctor updatedDoctor = doctorRepository.save(terminatedDoctor);
        DoctorResponseDto responseDto = updatedDoctor.toDto();
        Result<DoctorResponseDto> success = Result.success(responseDto);
        return ResponseEntity.ok(success);
    }

    private Result<DoctorResponseDto> onNotFoundDoctor() {
        return Result.error(ErrorMessages.DOCTOR_NOT_FOUND.value());
    }

    private Result<DoctorResponseDto> onTerminatedDoctor() {
        return Result.error(ErrorMessages.TERMINATED_DOCTOR.value());
    }

    private Result<DoctorResponseDto> onActiveDoctor() {
        return Result.error(ErrorMessages.ACTIVE_DOCTOR.value());
    }
}
