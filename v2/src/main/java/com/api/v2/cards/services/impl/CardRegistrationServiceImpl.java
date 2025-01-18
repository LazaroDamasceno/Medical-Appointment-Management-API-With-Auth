package com.api.v2.cards.services.impl;

import com.api.v2.cards.domain.Card;
import com.api.v2.cards.domain.CardRepository;
import com.api.v2.cards.dtos.CardRegistrationDto;
import com.api.v2.cards.dtos.CardResponseDto;
import com.api.v2.cards.services.interfaces.CardRegistrationService;
import com.api.v2.cards.utils.CardResponseMapper;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CardRegistrationServiceImpl implements CardRegistrationService {

    private final CardRepository cardRepository;

    public CardRegistrationServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public Mono<CardResponseDto> registerCreditCard(@Valid CardRegistrationDto registrationDto) {
        return Mono.defer(() -> {
                    Card card = Card.create(
                            "credit card",
                            registrationDto
                    );
                    return cardRepository.save(card);
                })
                .flatMap(CardResponseMapper::mapToMono);
    }

    @Override
    public Mono<CardResponseDto> registerDebitCard(@Valid CardRegistrationDto registrationDto) {
        return Mono.defer(() -> {
                    Card card = Card.create(
                            "debit card",
                            registrationDto
                    );
                    return cardRepository.save(card);
                })
                .flatMap(CardResponseMapper::mapToMono);
    }
}
