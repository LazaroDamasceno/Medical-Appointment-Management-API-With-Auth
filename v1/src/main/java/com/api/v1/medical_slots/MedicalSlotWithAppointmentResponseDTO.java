package com.api.v1.medical_slots;

import com.api.v1.doctors.DoctorResponseDTO;
import com.api.v1.medical_appointments.MedicalAppointment;
import com.api.v1.medical_slots.enums.MedicalSlotStatus;

import java.time.LocalDateTime;

public final class MedicalSlotWithAppointmentResponseDTO extends DefaultMedicalSlotResponseDTO {

    private final MedicalAppointment medicalAppointment;

    public MedicalSlotWithAppointmentResponseDTO(MedicalAppointment medicalAppointment) {
        this.medicalAppointment = medicalAppointment;
    }

    MedicalSlotWithAppointmentResponseDTO(String id,
                                          MedicalSlotStatus status,
                                          DoctorResponseDTO doctor,
                                          LocalDateTime availableAt,
                                          LocalDateTime createdAt,
                                          MedicalAppointment medicalAppointment
    ) {
        super(id, status, doctor, availableAt, createdAt);
        this.medicalAppointment = medicalAppointment;
    }

    public MedicalAppointment getMedicalAppointment() {
        return medicalAppointment;
    }
}
