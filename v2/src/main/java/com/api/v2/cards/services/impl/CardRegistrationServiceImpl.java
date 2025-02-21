package com.api.v2.cards.services.impl;

import com.api.v2.cards.controller.CardController;
import com.api.v2.cards.domain.Card;
import com.api.v2.cards.domain.CardRepository;
import com.api.v2.cards.dtos.CardRegistrationDto;
import com.api.v2.cards.dtos.CardResponseDto;
import com.api.v2.cards.services.interfaces.CardRegistrationService;
import com.api.v2.cards.utils.CardResponseMapper;
import de.kamillionlabs.hateoflux.model.hal.HalResourceWrapper;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static de.kamillionlabs.hateoflux.linkbuilder.SpringControllerLinkBuilder.linkTo;

@Service
public class CardRegistrationServiceImpl implements CardRegistrationService {

    private final CardRepository cardRepository;

    public CardRegistrationServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public Mono<HalResourceWrapper<CardResponseDto, Void>> registerCreditCard(@Valid CardRegistrationDto registrationDto) {
        return Mono.defer(() -> {
                    Card card = Card.of(
                            "credit card",
                            registrationDto
                    );
                    return cardRepository.save(card);
                })
                .flatMap(CardResponseMapper::mapToMono)
                .map(dto -> {
                    return HalResourceWrapper
                            .wrap(dto)
                            .withLinks(
                                    linkTo(
                                            CardController.class,
                                            controller -> controller.findById(dto.id())
                                    ).withRel("find_card_by_id"),
                                    linkTo(
                                            CardController.class,
                                            controller -> controller.delete(dto.id())
                                    ).withRel("delete_card_by_id")
                            );
                });
    }

    @Override
    public Mono<HalResourceWrapper<CardResponseDto, Void>> registerDebitCard(@Valid CardRegistrationDto registrationDto) {
        return Mono.defer(() -> {
                    Card card = Card.of(
                            "debit card",
                            registrationDto
                    );
                    return cardRepository.save(card);
                })
                .flatMap(CardResponseMapper::mapToMono)
                .map(dto -> {
                    return HalResourceWrapper
                            .wrap(dto)
                            .withLinks(
                                    linkTo(
                                            CardController.class,
                                            controller -> controller.findById(dto.id())
                                    ).withRel("find_card_by_id"),
                                    linkTo(
                                            CardController.class,
                                            controller -> controller.delete(dto.id())
                                    ).withRel("delete_card_by_id")
                            );
                });
    }
}
