package com.api.v1.payments.domain;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PaymentRepository extends ReactiveMongoRepository<Payment, String> {
}
