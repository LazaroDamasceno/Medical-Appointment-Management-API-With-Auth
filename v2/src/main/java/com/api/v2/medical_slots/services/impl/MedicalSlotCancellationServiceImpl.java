package com.api.v2.medical_slots.services.impl;

import com.api.v2.medical_appointments.domain.MedicalAppointment;
import com.api.v2.medical_appointments.domain.MedicalAppointmentRepository;
import com.api.v2.medical_appointments.exceptions.ImmutableMedicalAppointmentException;
import com.api.v2.medical_slots.domain.MedicalSlot;
import com.api.v2.medical_slots.domain.MedicalSlotRepository;
import com.api.v2.medical_slots.services.interfaces.MedicalSlotCancellationService;
import com.api.v2.medical_slots.utils.MedicalSlotFinderUtil;
import com.api.v2.telegram_bot.services.interfaces.TelegramBotMessageSenderService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicReference;

@Service
public class MedicalSlotCancellationServiceImpl implements MedicalSlotCancellationService {

    private final MedicalSlotFinderUtil medicalSlotFinderUtil;
    private final MedicalSlotRepository medicalSlotRepository;
    private final MedicalAppointmentRepository medicalAppointmentRepository;

    public MedicalSlotCancellationServiceImpl(
            MedicalSlotFinderUtil medicalSlotFinderUtil,
            MedicalSlotRepository medicalSlotRepository,
            TelegramBotMessageSenderService messageSenderService,
            MedicalAppointmentRepository medicalAppointmentRepository
    ) {
        this.medicalSlotFinderUtil = medicalSlotFinderUtil;
        this.medicalSlotRepository = medicalSlotRepository;
        this.medicalAppointmentRepository = medicalAppointmentRepository;
    }

    @Override
    public Mono<Void> cancel(String id) {
        return medicalSlotFinderUtil
                .findById(id)
                .flatMap(slot -> {
                    AtomicReference<String> message = new AtomicReference<>(
                            "Medical slot whose id is %s is already canceled.".formatted(id)
                    );
                    return onCanceledMedicalSlot(slot, message.get())
                            .then(Mono.defer(() -> {
                                message.set("Medical slot whose id is %s is already completed.".formatted(id));
                                return onCompletedMedicalSlot(slot, message.get());
                            }))
                            .then(Mono.defer(() -> {
                                MedicalAppointment medicalAppointment = slot.getMedicalAppointment();
                                medicalAppointment.markAsCanceled();
                                return medicalAppointmentRepository
                                        .save(medicalAppointment)
                                        .flatMap(canceledAppointment -> {
                                            slot.setMedicalAppointment(canceledAppointment);
                                            return medicalSlotRepository.save(slot);
                                        });
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
