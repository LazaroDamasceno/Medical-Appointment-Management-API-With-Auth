package com.api.v2.medical_slots.services.impl;

import com.api.v2.medical_appointments.domain.MedicalAppointment;
import com.api.v2.medical_appointments.domain.MedicalAppointmentRepository;
import com.api.v2.medical_slots.domain.MedicalSlotRepository;
import com.api.v2.medical_slots.services.interfaces.MedicalSlotCancellationService;
import com.api.v2.medical_slots.utils.MedicalSlotFinderUtil;
import com.api.v2.telegram_bot.services.interfaces.TelegramBotMessageSenderService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import reactor.core.publisher.Mono;

@Service
public class MedicalSlotCancellationServiceImpl implements MedicalSlotCancellationService {

    private final MedicalSlotFinderUtil medicalSlotFinderUtil;
    private final MedicalSlotRepository medicalSlotRepository;
    private final TelegramBotMessageSenderService messageSenderService;
    private final MedicalAppointmentRepository medicalAppointmentRepository;

    public MedicalSlotCancellationServiceImpl(
            MedicalSlotFinderUtil medicalSlotFinderUtil,
            MedicalSlotRepository medicalSlotRepository,
            TelegramBotMessageSenderService messageSenderService,
            MedicalAppointmentRepository medicalAppointmentRepository
    ) {
        this.medicalSlotFinderUtil = medicalSlotFinderUtil;
        this.medicalSlotRepository = medicalSlotRepository;
        this.messageSenderService = messageSenderService;
        this.medicalAppointmentRepository = medicalAppointmentRepository;
    }

    @Override
    public Mono<Void> cancel(String id) {
        return medicalSlotFinderUtil
                .findById(id)
                .flatMap(slot -> {
                    String message = "Medical slot whose id is %s was canceled.".formatted(slot.getId());
                    try {
                        messageSenderService.sendMessage(message);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    slot.markAsCanceled();
                    return medicalSlotRepository
                            .save(slot)
                            .then(Mono.defer(() -> {
                                slot.markAsCanceled();
                                return medicalSlotRepository
                                        .save(slot)
                                        .then(Mono.defer(() ->{
                                            MedicalAppointment medicalAppointment = slot.getMedicalAppointment();
                                            medicalAppointment.markAsCanceled();
                                            return medicalAppointmentRepository.save(medicalAppointment);
                                        }));
                            }));
                })
                .then();
    }
}
