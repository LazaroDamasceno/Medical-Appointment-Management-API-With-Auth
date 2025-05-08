package com.api.v1.medical_slots.controllers.exposed;

import com.api.v1.medical_slots.services.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class MedicalSlotController {

    protected MedicalSlotManagementService managementService;
    protected MedicalSlotRegistrationService registrationService;
    protected MedicalSlotRetrievalService retrievalService;

}
