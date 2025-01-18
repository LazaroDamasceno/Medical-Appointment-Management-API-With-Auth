package com.api.v2.cards.services.impl;

import com.api.v2.cards.domain.Card;
import com.api.v2.cards.domain.CardRepository;
import com.api.v2.cards.dtos.CardRegistrationDto;
import com.api.v2.cards.dtos.CardResponseDto;
import com.api.v2.cards.services.interfaces.CreditCardRegistrationService;
import com.api.v2.cards.utils.CardResponseMapper;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CreditCardRegistrationServiceImpl implements CreditCardRegistrationService {

    private final CardRepository cardRepository;

    public CreditCardRegistrationServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public Mono<CardResponseDto> register(@Valid CardRegistrationDto registrationDto) {
        return Mono.defer(() -> {
            Card card = Card.create(registrationDto);
            return cardRepository.save(card);
        })
        .flatMap(CardResponseMapper::mapToMono);
    }
}
