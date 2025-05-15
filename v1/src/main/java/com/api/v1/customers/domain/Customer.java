package com.api.v1.customers.domain;

import com.api.v1.customers.response.CustomerResponseDto;
import com.api.v1.people.domain.exposed.Person;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document
public class Customer {

    @Id
    String id;
    Person person;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

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

    public CustomerResponseDto toDto() {
        return CustomerResponseDto.from(this);
    }

    public String id() {
        return id;
    }

    public Person person() {
        return person;
    }

    public LocalDateTime createdAt() {
        return createdAt;
    }

    public LocalDateTime updatedAt() {
        return updatedAt;
    }
}
