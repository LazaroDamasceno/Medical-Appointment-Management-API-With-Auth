package com.api.v2.medical_slots.services.impl;

import com.api.v2.medical_appointments.domain.MedicalAppointment;
import com.api.v2.medical_slots.domain.MedicalSlot;
import com.api.v2.medical_slots.domain.MedicalSlotRepository;
import com.api.v2.medical_slots.services.interfaces.MedicalSlotCompletionService;
import com.api.v2.telegram_bot.services.interfaces.TelegramBotMessageSenderService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import reactor.core.publisher.Mono;

@Service
public class MedicalSlotCompletionServiceImpl implements MedicalSlotCompletionService {

    private final MedicalSlotRepository medicalSlotRepository;
    private final TelegramBotMessageSenderService messageSenderService;

    public MedicalSlotCompletionServiceImpl(MedicalSlotRepository medicalSlotRepository, TelegramBotMessageSenderService messageSenderService) {
        this.medicalSlotRepository = medicalSlotRepository;
        this.messageSenderService = messageSenderService;
    }

    @Override
    public Mono<Void> complete(MedicalSlot medicalSlot, MedicalAppointment medicalAppointment) {
        return Mono.defer(() -> {
            String message = "Medical slot whose id is %s is completed. It's immutable now.".formatted(medicalSlot.getId());
            try {
                messageSenderService.sendMessage(message);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
            medicalSlot.markAsCompleted();
            medicalSlot.setMedicalAppointment(medicalAppointment);
            return medicalSlotRepository
                    .save(medicalSlot)
                    .then();
        });
    }
}
