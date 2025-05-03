package com.api.v1.customers.domain.exposed;

import com.api.v1.people.domain.exposed.Person;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document
@Getter
@NoArgsConstructor
public final class Customer {

    @Id
    private String id;
    private Person person;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Customer(Person person) {
        this.id = UUID.randomUUID().toString();
        this.person = person;
        this.createdAt = LocalDateTime.now();
    }

    public static Customer of(Person person) {
        return new Customer(person);
    }

    public void update(Person person) {
        this.person = person;
        this.updatedAt = LocalDateTime.now();
    }
}
