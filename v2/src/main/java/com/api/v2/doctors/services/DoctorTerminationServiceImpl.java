package com.api.v2.doctors.services;

import com.api.v2.doctors.domain.DoctorAuditTrail;
import com.api.v2.doctors.domain.DoctorAuditTrailRepository;
import com.api.v2.doctors.domain.DoctorRepository;
import com.api.v2.doctors.exceptions.ImmutableDoctorException;
import com.api.v2.doctors.utils.DoctorFinderUtil;
import com.api.v2.telegram_bot.services.interfaces.TelegramBotMessageSenderService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import reactor.core.publisher.Mono;

@Service
public class DoctorTerminationServiceImpl implements DoctorTerminationService {

    private final DoctorFinderUtil doctorFinderUtil;
    private final DoctorRepository doctorRepository;
    private final DoctorAuditTrailRepository doctorAuditTrailRepository;
    private final TelegramBotMessageSenderService messageSenderService;

    public DoctorTerminationServiceImpl(
            DoctorFinderUtil doctorFinderUtil,
            DoctorRepository doctorRepository,
            DoctorAuditTrailRepository doctorAuditTrailRepository,
            TelegramBotMessageSenderService messageSenderService
    ) {
        this.doctorFinderUtil = doctorFinderUtil;
        this.doctorRepository = doctorRepository;
        this.doctorAuditTrailRepository = doctorAuditTrailRepository;
        this.messageSenderService = messageSenderService;
    }

    @Override
    public Mono<Void> terminate(String medicalLicenseNumber) {
        return doctorFinderUtil
                .findByLicenseNumber(medicalLicenseNumber)
                .flatMap(doctor -> {
                    if (doctor.getTerminatedAt() != null) {
                        String message = "Doctor whose license number is %s is already terminated.".formatted(doctor.getLicenseNumber());
                        return Mono.error(new ImmutableDoctorException(message));
                    }
                    String message = "Doctor whose license number is %s was terminated.".formatted(medicalLicenseNumber);
                    try {
                        messageSenderService.sendMessage(message);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }

                    DoctorAuditTrail auditTrail = DoctorAuditTrail.create(doctor);
                    return doctorAuditTrailRepository
                            .save(auditTrail)
                            .then(Mono.defer(() -> {
                                doctor.markAsTerminated();
                                return doctorRepository.save(doctor);
                            }));
                })
                .then();
    }
}
