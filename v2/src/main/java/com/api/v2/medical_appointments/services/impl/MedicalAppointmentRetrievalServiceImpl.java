package com.api.v2.medical_appointments.services.impl;

import com.api.v2.medical_appointments.domain.MedicalAppointmentRepository;
import com.api.v2.medical_appointments.dtos.MedicalAppointmentResponseDto;
import com.api.v2.medical_appointments.services.interfaces.MedicalAppointmentRetrievalService;
import com.api.v2.medical_appointments.utils.MedicalAppointmentFinderUtil;
import com.api.v2.medical_appointments.utils.MedicalAppointmentResponseMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MedicalAppointmentRetrievalServiceImpl implements MedicalAppointmentRetrievalService {

    private final MedicalAppointmentFinderUtil medicalAppointmentFinderUtil;
    private final MedicalAppointmentRepository medicalAppointmentRepository;

    public MedicalAppointmentRetrievalServiceImpl(
            MedicalAppointmentFinderUtil medicalAppointmentFinderUtil,
            MedicalAppointmentRepository medicalAppointmentRepository
    ) {
        this.medicalAppointmentFinderUtil = medicalAppointmentFinderUtil;
        this.medicalAppointmentRepository = medicalAppointmentRepository;
    }

    @Override
    public Mono<MedicalAppointmentResponseDto> findById(String id) {
        return medicalAppointmentFinderUtil
                .findById(id)
                .flatMap(MedicalAppointmentResponseMapper::mapToMono);
    }

    @Override
    public Flux<MedicalAppointmentResponseDto> findAll() {
        return medicalAppointmentRepository
                .findAll()
                .flatMap(MedicalAppointmentResponseMapper::mapToMono);
    }

    @Override
    public Flux<MedicalAppointmentResponseDto> findAllPublicInsurance() {
        return medicalAppointmentRepository
            .findAll()
            .filter(appointment -> appointment.getType().equals("public insurance"))
            .flatMap(MedicalAppointmentResponseMapper::mapToMono);
    }

    @Override
    public Flux<MedicalAppointmentResponseDto> findAllPrivateInsurance() {
        return medicalAppointmentRepository
                .findAll()
                .filter(appointment -> appointment.getType().equals("private insurance"))
                .flatMap(MedicalAppointmentResponseMapper::mapToMono);
    }

    @Override
    public Flux<MedicalAppointmentResponseDto> findAllPaidByPatient() {
        return medicalAppointmentRepository
                .findAll()
                .filter(appointment -> appointment.getType().equals("paid by patient"))
                .flatMap(MedicalAppointmentResponseMapper::mapToMono);
    }
}
