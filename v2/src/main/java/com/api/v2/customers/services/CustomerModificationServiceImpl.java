package com.api.v2.customers.services;

import com.api.v2.customers.domain.CustomerRepository;
import com.api.v2.customers.dtos.CustomerModificationDto;
import com.api.v2.customers.utils.CustomerFinderUtil;
import com.api.v2.people.services.interfaces.PersonModificationService;
import com.api.v2.telegram_bot.services.interfaces.TelegramBotMessageSenderService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import reactor.core.publisher.Mono;

@Service
public class CustomerModificationServiceImpl implements CustomerModificationService {

    private final CustomerRepository customerRepository;
    private final PersonModificationService personModificationService;
    private final CustomerFinderUtil customerFinderUtil;
    private final TelegramBotMessageSenderService messageSenderService;

    public CustomerModificationServiceImpl(
            CustomerRepository customerRepository,
            PersonModificationService personModificationService,
            CustomerFinderUtil customerFinderUtil,
            TelegramBotMessageSenderService messageSenderService
    ) {
        this.customerRepository = customerRepository;
        this.personModificationService = personModificationService;
        this.customerFinderUtil = customerFinderUtil;
        this.messageSenderService = messageSenderService;
    }

    @Override
    public Mono<Void> modify(String ssn, @Valid CustomerModificationDto modificationDto) {
        return customerFinderUtil
                .findBySsn(ssn)
                .flatMap(customer -> {
                    return personModificationService
                            .modify(customer.getPerson(), modificationDto.personModificationDto())
                            .flatMap(modifiedPerson -> {
                                String message = "Customer whose SSN is %s was modified.".formatted(ssn);
                                try {
                                    messageSenderService.sendMessage(message);
                                } catch (TelegramApiException e) {
                                    throw new RuntimeException(e);
                                }
                                customer.setPerson(modifiedPerson);
                               return customerRepository.save(customer);
                            });
                })
                .then();
    }
}
