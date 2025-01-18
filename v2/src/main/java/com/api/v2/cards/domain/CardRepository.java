package com.api.v2.cards.domain;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CardRepository extends ReactiveMongoRepository<Card, ObjectId> {
}
