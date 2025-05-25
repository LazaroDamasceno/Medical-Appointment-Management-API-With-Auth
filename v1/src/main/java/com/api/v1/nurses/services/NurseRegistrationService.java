package com.api.v1.nurses.services;

import com.api.v1.nurses.requests.NurseRegistrationDTO;
import com.api.v1.nurses.responses.NurseResponseDTO;
import org.springframework.http.ResponseEntity;

public interface NurseRegistrationService {
    ResponseEntity<NurseResponseDTO> register(NurseRegistrationDTO registrationDTO);
}
