package com.api.v2.cards.services.interfaces;

import com.api.v2.cards.dtos.CardRegistrationDto;
import com.api.v2.cards.dtos.CardResponseDto;
import reactor.core.publisher.Mono;

public interface CreditCardRegistrationService {
    Mono<CardResponseDto> register(CardRegistrationDto registrationDto);
}
