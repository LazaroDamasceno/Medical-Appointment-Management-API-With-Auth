package com.api.v2.customers.domain;

import com.api.v2.customers.domain.exposed.Customer;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.UUID;

public record CustomerAuditTrail(
        @Id
        String id,
        Customer customer,
        LocalDateTime createdAt
) {

    public static CustomerAuditTrail of(Customer customer) {
        return new CustomerAuditTrail(
                UUID.randomUUID().toString(),
                customer,
                LocalDateTime.now()
        );
    }
}
