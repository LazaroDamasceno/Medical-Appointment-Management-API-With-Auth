package com.api.v2.customers.domain;

import com.api.v2.common.Address;
import com.api.v2.people.domain.Person;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Document
public class Customer {

    @BsonId
    private ObjectId id;
    private Address address;
    private Person person;
    private LocalDateTime createdAt;
    private ZoneId createdAtZone;

    public Customer() {
    }

    private Customer(Address address, Person person) {
        this.id = new ObjectId();
        this.address = address;
        this.person = person;
        this.createdAt = LocalDateTime.now();
        this.createdAtZone = ZoneId.systemDefault();
    }

    public static Customer create(Address address, Person person) {
        return new Customer(address, person);
    }

    public ObjectId getId() {
        return id;
    }

    public Address getAddress() {
        return address;
    }

    public Person getPerson() {
        return person;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public ZoneId getCreatedAtZone() {
        return createdAtZone;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
