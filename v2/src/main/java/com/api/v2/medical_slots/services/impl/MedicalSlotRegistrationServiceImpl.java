package com.api.v2.medical_slots.services.impl;

import com.api.v2.doctors.domain.Doctor;
import com.api.v2.doctors.utils.DoctorFinderUtil;
import com.api.v2.medical_slots.domain.MedicalSlot;
import com.api.v2.medical_slots.domain.MedicalSlotRepository;
import com.api.v2.medical_slots.dtos.MedicalSlotRegistrationDto;
import com.api.v2.medical_slots.dtos.MedicalSlotResponseDto;
import com.api.v2.medical_slots.exceptions.UnavailableMedicalSlotException;
import com.api.v2.medical_slots.services.interfaces.MedicalSlotRegistrationService;
import com.api.v2.medical_slots.utils.MedicalSlotFinderUtil;
import com.api.v2.medical_slots.utils.MedicalSlotResponseMapper;
import com.api.v2.telegram_bot.services.interfaces.TelegramBotMessageSenderService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class MedicalSlotRegistrationServiceImpl implements MedicalSlotRegistrationService {

    private final MedicalSlotRepository medicalSlotRepository;
    private final TelegramBotMessageSenderService messageSenderService;
    private final DoctorFinderUtil doctorFinderUtil;
    private final MedicalSlotFinderUtil medicalSlotFinderUtil;

    public MedicalSlotRegistrationServiceImpl(
            MedicalSlotRepository medicalSlotRepository,
            TelegramBotMessageSenderService messageSenderService,
            DoctorFinderUtil doctorFinderUtil,
            MedicalSlotFinderUtil medicalSlotFinderUtil
    ) {
        this.medicalSlotRepository = medicalSlotRepository;
        this.messageSenderService = messageSenderService;
        this.doctorFinderUtil = doctorFinderUtil;
        this.medicalSlotFinderUtil = medicalSlotFinderUtil;
    }

    @Override
    public Mono<MedicalSlotResponseDto> register(@Valid MedicalSlotRegistrationDto registrationDto) {
        return doctorFinderUtil
                .findByLicenseNumber(registrationDto.medicalLicenseNumber())
                .flatMap(doctor -> {
                    return onUnavailableMedicalSlot(doctor, registrationDto.availableAt())
                            .then(Mono.defer(() -> {
                                return Mono.defer(() -> {
                                    String message = "A new medical slot was registered. Its datetime is %s.".formatted(registrationDto.availableAt());
                                    try {
                                        messageSenderService.sendMessage(message);
                                    } catch (TelegramApiException e) {
                                        throw new RuntimeException(e);
                                    }
                                    MedicalSlot medicalSlot = MedicalSlot.create(doctor, registrationDto.availableAt());
                                    return medicalSlotRepository.save(medicalSlot);
                                });
                            }))
                            .flatMap(MedicalSlotResponseMapper::mapToMono);
                });
    }

    private Mono<Void> onUnavailableMedicalSlot(Doctor doctor, LocalDateTime availableAt) {
        return medicalSlotFinderUtil
                .findActiveByDoctorAndAvailableAt(doctor, availableAt)
                .hasElement()
                .flatMap(exists -> {
                    System.out.println(exists);
                    if (exists) {
                        String message = "The given datetime %s is already registered for another medical slot".formatted(availableAt);
                        return Mono.error(new UnavailableMedicalSlotException(message));
                    }
                    return Mono.empty();
                });
    }
}
