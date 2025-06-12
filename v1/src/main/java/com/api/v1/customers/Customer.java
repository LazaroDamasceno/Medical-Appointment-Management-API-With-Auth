package com.api.v1.customers;

import com.api.v1.people.Person;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "Customers")
public record Customer(
        @Id
        @Indexed(unique = true)
        String id,
        Person person,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static Customer of(Person person) {
        return new Customer(
                UUID.randomUUID().toString(),
                person,
                LocalDateTime.now(),
                null
        );
    }

    public Customer update(Person person) {
        return new Customer(
                this.id,
                person,
                this.createdAt,
                LocalDateTime.now()
        );
    }

    public CustomerResponseDTO toDto() {
        return CustomerResponseDTO.from(this);
    }
}
