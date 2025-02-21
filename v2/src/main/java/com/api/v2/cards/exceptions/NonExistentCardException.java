package com.api.v2.cards.exceptions;

import com.api.v2.common.Id;

public class NonExistentCardException extends RuntimeException {
    public NonExistentCardException(@Id String id) {
        super("Card whose id is %s was not found.".formatted(id));
    }
}
