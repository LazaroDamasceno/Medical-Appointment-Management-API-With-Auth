package com.api.v1.doctors.services;

import com.api.v1.common.LicenseNumber;
import com.api.v1.doctors.domain.DoctorCrudRepository;
import com.api.v1.doctors.domain.Doctor;
import com.api.v1.doctors.responses.DoctorResponseDto;
import com.api.v1.doctors.utils.DoctorFinder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DoctorRetrievalServiceImpl implements DoctorRetrievalService {

    private final DoctorFinder finder;
    private final DoctorCrudRepository repository;

    public DoctorRetrievalServiceImpl(DoctorFinder finder, DoctorCrudRepository repository) {
        this.finder = finder;
        this.repository = repository;
    }

    @Override
    public ResponseEntity<DoctorResponseDto> findByLicenseNumber(@LicenseNumber String licenseNumber) {
        Doctor foundDoctor = finder.findByLicenseNumber(licenseNumber);
        DoctorResponseDto responseDto = foundDoctor.toDto();
        return ResponseEntity.ok(responseDto);
    }

    @Override
    public ResponseEntity<Page<DoctorResponseDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(repository.findAll(pageable).map(Doctor::toDto));
    }
}
