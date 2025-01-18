package com.api.v2.cards.services.impl;

import com.api.v2.cards.domain.CardRepository;
import com.api.v2.cards.services.interfaces.CardDeletionService;
import com.api.v2.cards.utils.CardFinderUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CardDeletionServiceImpl implements CardDeletionService {

    private final CardRepository cardRepository;
    private final CardFinderUtil cardFinderUtil;

    public CardDeletionServiceImpl(
            CardRepository cardRepository,
            CardFinderUtil cardFinderUtil
    ) {
        this.cardRepository = cardRepository;
        this.cardFinderUtil = cardFinderUtil;
    }

    @Override
    public Mono<Void> delete(String id) {
        return cardFinderUtil
                .find(id)
                .flatMap(card -> {
                    card.bookDeletion();
                    return cardRepository.save(card);
                })
                .then();
    }
}
