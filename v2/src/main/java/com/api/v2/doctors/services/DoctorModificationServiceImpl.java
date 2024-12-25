package com.api.v2.doctors.services;

import com.api.v2.doctors.domain.DoctorRepository;
import com.api.v2.doctors.utils.DoctorFinderUtil;
import com.api.v2.people.dtos.PersonModificationDto;
import com.api.v2.people.services.interfaces.PersonModificationService;
import com.api.v2.telegram_bot.services.interfaces.TelegramBotMessageSenderService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import reactor.core.publisher.Mono;

@Service
public class DoctorModificationServiceImpl implements DoctorModificationService {

    private final DoctorFinderUtil doctorFinderUtil;
    private final DoctorRepository doctorRepository;
    private final PersonModificationService personModificationService;
    private final TelegramBotMessageSenderService messageSenderService;

    public DoctorModificationServiceImpl(
            DoctorFinderUtil doctorFinderUtil,
            DoctorRepository doctorRepository,
            PersonModificationService personModificationService,
            TelegramBotMessageSenderService messageSenderService
    ) {
        this.doctorFinderUtil = doctorFinderUtil;
        this.doctorRepository = doctorRepository;
        this.personModificationService = personModificationService;
        this.messageSenderService = messageSenderService;
    }

    @Override
    public Mono<Void> modify(String medicalLicenseNumber, @Valid PersonModificationDto modificationDto) {
        return doctorFinderUtil
                .findByLicenseNumber(medicalLicenseNumber)
                .flatMap(doctor -> {
                    return personModificationService
                            .modify(doctor.getPerson(), modificationDto)
                            .flatMap(modifiedPerson -> {
                                String message = "Doctor whose license number is %s was modified.".formatted(medicalLicenseNumber);
                                try {
                                    messageSenderService.sendMessage(message);
                                } catch (TelegramApiException e) {
                                    throw new RuntimeException(e);
                                }
                                doctor.setPerson(modifiedPerson);
                                return doctorRepository.save(doctor);
                            });
                })
                .then();
    }
}
