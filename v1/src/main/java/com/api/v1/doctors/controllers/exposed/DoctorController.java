package com.api.v1.doctors.controllers.exposed;

import com.api.v1.doctors.services.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class DoctorController {

    protected DoctorManagementService managementService;
    protected DoctorRegistrationService registrationService;
    protected DoctorRetrievalService retrievalService;
    protected DoctorUpdatingService updatingService;

}
