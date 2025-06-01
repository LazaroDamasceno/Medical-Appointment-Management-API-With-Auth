package com.api.v1.customers;

import com.api.v1.people.Person;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "Customers")
public class Customer {

    @Id
    @Indexed(unique = true)
    private String id;
    private Person person;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Customer() {}

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

    public CustomerResponseDTO toDto() {
        return CustomerResponseDTO.from(this);
    }

    public String getId() {
        return id;
    }

    public Person getPerson() {
        return person;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
