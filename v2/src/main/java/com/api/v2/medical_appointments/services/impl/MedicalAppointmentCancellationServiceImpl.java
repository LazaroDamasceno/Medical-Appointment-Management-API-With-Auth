package com.api.v2.medical_appointments.services.impl;

import com.api.v2.medical_appointments.domain.MedicalAppointmentRepository;
import com.api.v2.medical_appointments.services.interfaces.MedicalAppointmentCancellationService;
import com.api.v2.medical_appointments.utils.MedicalAppointmentFinderUtil;
import com.api.v2.medical_slots.domain.MedicalSlotRepository;
import com.api.v2.medical_slots.utils.MedicalSlotFinderUtil;
import com.api.v2.telegram_bot.services.interfaces.TelegramBotMessageSenderService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import reactor.core.publisher.Mono;

@Service
public class MedicalAppointmentCancellationServiceImpl implements MedicalAppointmentCancellationService {

    private final MedicalSlotFinderUtil medicalSlotFinderUtil;
    private final MedicalAppointmentFinderUtil medicalAppointmentFinderUtil;
    private final MedicalSlotRepository medicalSlotRepository;
    private final MedicalAppointmentRepository medicalAppointmentRepository;
    private final TelegramBotMessageSenderService messageSenderService;

    public MedicalAppointmentCancellationServiceImpl(
            MedicalSlotFinderUtil medicalSlotFinderUtil,
            MedicalAppointmentFinderUtil medicalAppointmentFinderUtil,
            MedicalSlotRepository medicalSlotRepository,
            MedicalAppointmentRepository medicalAppointmentRepository,
            TelegramBotMessageSenderService messageSenderService
    ) {
        this.medicalSlotFinderUtil = medicalSlotFinderUtil;
        this.medicalAppointmentFinderUtil = medicalAppointmentFinderUtil;
        this.medicalSlotRepository = medicalSlotRepository;
        this.medicalAppointmentRepository = medicalAppointmentRepository;
        this.messageSenderService = messageSenderService;
    }

    @Override
    public Mono<Void> cancel(String id) {
        return medicalAppointmentFinderUtil
                .findById(id)
                .flatMap(medicalAppointment -> {
                    try {
                        String message = "Medical appointment";
                        messageSenderService.sendMessage("");
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    return medicalSlotFinderUtil
                            .findByMedicalAppointment(medicalAppointment)
                            .flatMap(medicalSlot -> {
                                medicalSlot.markAsCanceled();
                                medicalSlot.setMedicalAppointment(null);
                                return medicalSlotRepository.save(medicalSlot);
                            })
                            .then(Mono.defer(() -> {
                                medicalAppointment.markAsCanceled();
                                return medicalAppointmentRepository.save(medicalAppointment);
                            }));
                })
                .then();
    }
}
