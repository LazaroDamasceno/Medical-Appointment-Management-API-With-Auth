package com.api.v2.medical_slots.services.impl;

import com.api.v2.medical_appointments.exceptions.ImmutableMedicalAppointmentException;
import com.api.v2.medical_slots.domain.MedicalSlot;
import com.api.v2.medical_slots.domain.MedicalSlotRepository;
import com.api.v2.medical_slots.services.interfaces.MedicalSlotCompletionService;
import com.api.v2.medical_slots.utils.MedicalSlotFinderUtil;
import com.api.v2.telegram_bot.services.interfaces.TelegramBotMessageSenderService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicReference;

@Service
public class MedicalSlotCompletionServiceImpl implements MedicalSlotCompletionService {

    private final MedicalSlotFinderUtil medicalSlotFinderUtil;
    private final MedicalSlotRepository medicalSlotRepository;
    private final TelegramBotMessageSenderService telegramBotMessageSenderService;

    public MedicalSlotCompletionServiceImpl(
            MedicalSlotFinderUtil medicalSlotFinderUtil,
            MedicalSlotRepository medicalSlotRepository,
            TelegramBotMessageSenderService telegramBotMessageSenderService
    ) {
        this.medicalSlotFinderUtil = medicalSlotFinderUtil;
        this.medicalSlotRepository = medicalSlotRepository;
        this.telegramBotMessageSenderService = telegramBotMessageSenderService;
    }

    @Override
    public Mono<Void> complete(String id) {
        return medicalSlotFinderUtil
                .findById(id)
                .flatMap(slot -> {
                    AtomicReference<String> message = new AtomicReference<>("Medical slot whose id is %s is already canceled.".formatted(id));
                    return onCanceledMedicalSlot(slot, message.get())
                            .then(Mono.defer(() -> {
                                message.set("Medical slot whose id is %s is already completed.".formatted(id));
                                return onCompletedMedicalSlot(slot, message.get());
                            }))
                            .then(Mono.defer(() -> {
                                message.set("Medical slot whose id is %s is was marked as completed. It's immutable now.".formatted(id));
                                try {
                                    telegramBotMessageSenderService.sendMessage(message.get());
                                } catch (TelegramApiException e) {
                                    throw new RuntimeException(e);
                                }
                                slot.markAsCompleted();
                                return medicalSlotRepository.save(slot);
                            }));
                })
                .then();
    }

    private Mono<Void> onCanceledMedicalSlot(MedicalSlot slot, String errorMessage) {
        if (slot.getCompletedAt() == null && slot.getCanceledAt() != null) {
            String message = "Medical appointment whose id is %s is already canceled.".formatted(slot.getId());
            return Mono.error(new ImmutableMedicalAppointmentException(message));
        }
        return Mono.empty();
    }

    private Mono<Void> onCompletedMedicalSlot(MedicalSlot slot, String errorMessage) {
        if (slot.getCompletedAt() != null && slot.getCanceledAt() == null) {
            String message = "Medical appointment whose id is %s is already completed.".formatted(slot.getId());
            return Mono.error(new ImmutableMedicalAppointmentException(message));
        }
        return Mono.empty();
    }
}
