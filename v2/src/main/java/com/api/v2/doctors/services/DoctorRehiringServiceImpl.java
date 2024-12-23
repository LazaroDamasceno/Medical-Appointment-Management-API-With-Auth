package com.api.v2.doctors.services;

import com.api.v2.doctors.domain.DoctorRepository;
import com.api.v2.doctors.exceptions.ImmutableDoctorException;
import com.api.v2.doctors.utils.DoctorFinderUtil;
import com.api.v2.telegram_bot.services.interfaces.TelegramBotMessageSenderService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import reactor.core.publisher.Mono;

@Service
public class DoctorRehiringServiceImpl implements DoctorRehiringService {

    private final DoctorFinderUtil doctorFinderUtil;
    private final DoctorRepository doctorRepository;
    private final TelegramBotMessageSenderService messageSenderService;

    public DoctorRehiringServiceImpl(
            DoctorFinderUtil doctorFinderUtil,
            DoctorRepository doctorRepository,
            TelegramBotMessageSenderService messageSenderService
    ) {
        this.doctorFinderUtil = doctorFinderUtil;
        this.doctorRepository = doctorRepository;
        this.messageSenderService = messageSenderService;
    }

    @Override
    public Mono<Void> rehire(String medicalLicenseNumber) {
        return doctorFinderUtil
                .findByLicenseNumber(medicalLicenseNumber)
                .flatMap(doctor -> {
                    if (doctor.getTerminatedAt() == null) {
                        String message = "Doctor whose license number is %s is employed.".formatted(doctor.getLicenseNumber());
                        return Mono.error(new ImmutableDoctorException(message));
                    }
                    String message = "Doctor whose license number is %s was rehired.".formatted(medicalLicenseNumber);
                    try {
                        messageSenderService.sendMessage(message);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    doctor.markAsRehired();
                    return doctorRepository.save(doctor);
                })
                .then();
    }
}
