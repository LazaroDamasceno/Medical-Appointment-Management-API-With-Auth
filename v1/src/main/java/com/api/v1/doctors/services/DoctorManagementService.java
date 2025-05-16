package com.api.v1.doctors.services;

import com.api.v1.common.Result;
import com.api.v1.doctors.response.DoctorResponseDto;
import org.springframework.http.ResponseEntity;

public interface DoctorManagementService {
    ResponseEntity<Result<DoctorResponseDto>> terminate(String licenseNumber);
    ResponseEntity<Result<DoctorResponseDto>> rehire(String licenseNumber);
}
