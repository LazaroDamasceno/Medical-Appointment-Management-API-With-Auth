package com.api.v2.cards.services.interfaces;

import com.api.v2.cards.dtos.CardRegistrationDto;
import com.api.v2.cards.dtos.CardResponseDto;
import reactor.core.publisher.Mono;

public interface CardRegistrationService {
    Mono<CardResponseDto> registerCreditCard(CardRegistrationDto registrationDto);
    Mono<CardResponseDto> registerDebitCard(CardRegistrationDto registrationDto);

}
