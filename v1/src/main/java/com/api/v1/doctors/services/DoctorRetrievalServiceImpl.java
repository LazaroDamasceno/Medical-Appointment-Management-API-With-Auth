package com.api.v1.doctors.services;

import com.api.v1.common.LicenseNumber;
import com.api.v1.doctors.domain.DoctorRepository;
import com.api.v1.doctors.domain.exposed.Doctor;
import com.api.v1.doctors.response.DoctorResponseDto;
import com.api.v1.doctors.utils.exposed.DoctorFinder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DoctorRetrievalServiceImpl implements DoctorRetrievalService {

    private final DoctorFinder finder;
    private final DoctorRepository repository;

    public DoctorRetrievalServiceImpl(DoctorFinder finder, DoctorRepository repository) {
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
