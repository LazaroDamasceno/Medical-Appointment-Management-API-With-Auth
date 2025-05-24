package com.api.v1.nurses.services;

import com.api.v1.common.LicenseNumber;
import com.api.v1.nurses.domain.NurseCrudRepository;
import com.api.v1.nurses.domain.exposed.Nurse;
import com.api.v1.nurses.responses.NurseResponseDTO;
import com.api.v1.nurses.utils.exposed.NurseFinder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class NurseRetrievalServiceImpl implements NurseRetrievalService {

    private final NurseFinder nurseFinder;
    private final NurseCrudRepository crudRepository;

    public NurseRetrievalServiceImpl(NurseFinder nurseFinder,
                                     NurseCrudRepository crudRepository
    ) {
        this.nurseFinder = nurseFinder;
        this.crudRepository = crudRepository;
    }

    @Override
    public ResponseEntity<NurseResponseDTO> findByLicenseNumber(@LicenseNumber String licenseNumber) {
        Nurse foundNurse = nurseFinder.findByLicenseNumber(licenseNumber);
        NurseResponseDTO dto = foundNurse.toDTO();
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<Page<NurseResponseDTO>> findAll(Pageable pageable) {
        Page<NurseResponseDTO> all = crudRepository
                .findAll(pageable)
                .map(Nurse::toDTO);
        return ResponseEntity.ok(all);
    }
}
