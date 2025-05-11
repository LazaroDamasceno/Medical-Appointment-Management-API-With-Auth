package com.api.v1.medical_appointments.responses.ordinary_appointments;

import com.api.v1.customers.responses.CustomerResponseDto;
import com.api.v1.doctors.responses.DoctorResponseDto;
import com.api.v1.medical_appointments.domain.ordinaty_appointments.exposed.MedicalAppointment;
import com.api.v1.medical_appointments.enums.MedicalAppointmentStatus;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

public class MedicalAppointmentResponseDto extends RepresentationModel<MedicalAppointmentResponseDto> {

    private String id;
    private CustomerResponseDto customer;
    private DoctorResponseDto doctor;
    private MedicalAppointmentStatus status;
    private LocalDateTime bookedAt;

    protected MedicalAppointmentResponseDto() {}

    protected MedicalAppointmentResponseDto(String id,
                                          CustomerResponseDto customer,
                                          DoctorResponseDto doctor,
                                          MedicalAppointmentStatus status,
                                          LocalDateTime bookedAt
    ) {
        this.id = id;
        this.customer = customer;
        this.doctor = doctor;
        this.status = status;
        this.bookedAt = bookedAt;
    }

    public static MedicalAppointmentResponseDto from(MedicalAppointment medicalAppointment) {
        return new MedicalAppointmentResponseDto(
                medicalAppointment.getId(),
                medicalAppointment.getCustomer().toDto(),
                medicalAppointment.getDoctor().toDto(),
                medicalAppointment.getStatus(),
                medicalAppointment.getBookedAt()
        );
    }

    public String getId() {
        return id;
    }

    public CustomerResponseDto getCustomer() {
        return customer;
    }

    public DoctorResponseDto getDoctor() {
        return doctor;
    }

    public MedicalAppointmentStatus getStatus() {
        return status;
    }

    public LocalDateTime getBookedAt() {
        return bookedAt;
    }
}
