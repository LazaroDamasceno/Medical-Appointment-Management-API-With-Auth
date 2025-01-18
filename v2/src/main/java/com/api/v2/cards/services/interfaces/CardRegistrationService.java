package com.api.v2.cards.services.interfaces;

import com.api.v2.cards.dtos.CardRegistrationDto;
import com.api.v2.cards.dtos.CardResponseDto;
import de.kamillionlabs.hateoflux.model.hal.HalResourceWrapper;
import reactor.core.publisher.Mono;

public interface CardRegistrationService {
    Mono<HalResourceWrapper<CardResponseDto, Void>> registerCreditCard(CardRegistrationDto registrationDto);
    Mono<HalResourceWrapper<CardResponseDto, Void>> registerDebitCard(CardRegistrationDto registrationDto);

}
