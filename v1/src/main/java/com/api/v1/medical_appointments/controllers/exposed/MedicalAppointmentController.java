package com.api.v1.medical_appointments.controllers.exposed;

import com.api.v1.medical_appointments.services.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class MedicalAppointmentController {

    protected MedicalAppointmentCancellationService cancellationService;
    protected MedicalAppointmentRegistrationService registrationService;
    protected MedicalAppointmentRetrievalService retrievalService;

}
