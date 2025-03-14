package com.api.v2.cards.controller;

import com.api.v2.cards.dtos.CardRegistrationDto;
import com.api.v2.cards.dtos.CardResponseDto;
import com.api.v2.cards.services.interfaces.CardDeletionService;
import com.api.v2.cards.services.interfaces.CardRegistrationService;
import com.api.v2.cards.services.interfaces.CardRetrievalService;

import de.kamillionlabs.hateoflux.model.hal.HalResourceWrapper;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v2/cards")
public class CardController {

    private final CardRegistrationService registrationService;
    private final CardDeletionService deletionService;
    private final CardRetrievalService retrievalService;

    public CardController(
            CardRegistrationService registrationService,
            CardDeletionService deletionService,
            CardRetrievalService retrievalService
    ) {
        this.registrationService = registrationService;
        this.deletionService = deletionService;
        this.retrievalService = retrievalService;
    }

    @PostMapping("credit-card")
    public Mono<HalResourceWrapper<CardResponseDto, Void>> registerCreditCard(@Valid @RequestBody CardRegistrationDto registrationDto) {
        return registrationService.registerCreditCard(registrationDto);
    }

    @PostMapping("debit-card")
    public Mono<HalResourceWrapper<CardResponseDto, Void>> registerDebitCard(@Valid @RequestBody CardRegistrationDto registrationDto) {
        return registrationService.registerDebitCard(registrationDto);
    }

    @DeleteMapping("{id}")
    public Mono<Void> delete(@PathVariable String id) {
        return deletionService.delete(id);
    }

    @GetMapping
    public Flux<HalResourceWrapper<CardResponseDto, Void>> findAll() {
        return retrievalService.findAll();
    }

    @GetMapping("{id}")
    public Mono<HalResourceWrapper<CardResponseDto, Void>> findById(@PathVariable String id) {
        return retrievalService.findById(id);
    }
}
