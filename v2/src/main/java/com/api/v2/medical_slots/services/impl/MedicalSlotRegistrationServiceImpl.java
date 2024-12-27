package com.api.v2.medical_slots.services.impl;

import com.api.v2.doctors.domain.Doctor;
import com.api.v2.doctors.utils.DoctorFinderUtil;
import com.api.v2.medical_slots.domain.MedicalSlot;
import com.api.v2.medical_slots.domain.MedicalSlotRepository;
import com.api.v2.medical_slots.dtos.MedicalSlotRegistrationDto;
import com.api.v2.medical_slots.dtos.MedicalSlotResponseDto;
import com.api.v2.medical_slots.exceptions.UnavailableMedicalSlotException;
import com.api.v2.medical_slots.services.interfaces.MedicalSlotRegistrationService;
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

    public MedicalSlotRegistrationServiceImpl(
            MedicalSlotRepository medicalSlotRepository,
            TelegramBotMessageSenderService messageSenderService,
            DoctorFinderUtil doctorFinderUtil
    ) {
        this.medicalSlotRepository = medicalSlotRepository;
        this.messageSenderService = messageSenderService;
        this.doctorFinderUtil = doctorFinderUtil;
    }

    @Override
    public Mono<MedicalSlotResponseDto> register(@Valid MedicalSlotRegistrationDto registrationDto) {
        return doctorFinderUtil
                .findByLicenseNumber(registrationDto.medicalLicenseNumber())
                .flatMap(doctor -> {
                    return onUnavailableMedicalSlot(registrationDto.availableAt(), doctor)
                            .then(Mono.defer(() -> {
                                return Mono.defer(() -> {
                                    String message = "A new medical slot was registered. Its datetime is %s."
                                            .formatted(registrationDto.availableAt());
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

    private Mono<Void> onUnavailableMedicalSlot(LocalDateTime availableAt, Doctor doctor) {
        return medicalSlotRepository
                .findActiveByDoctorAndAvailableAt(availableAt, doctor)
                .hasElement()
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new UnavailableMedicalSlotException(availableAt));
                    }
                    return Mono.empty();
                });
    }
}
