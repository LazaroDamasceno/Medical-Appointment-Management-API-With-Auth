package com.api.v2.domain;

import com.api.v2.common.AddressDto;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.ZoneId;

@Document
public class Customer {

    @BsonId
    private ObjectId id;
    private AddressDto address;
    private Person person;
    private LocalDate createdAt;
    private ZoneId createdAtZone;

    public Customer() {
    }

    private Customer(AddressDto address, Person person) {
        this.id = new ObjectId();
        this.address = address;
        this.person = person;
        this.createdAt = LocalDate.now();
        this.createdAtZone = ZoneId.systemDefault();
    }

    public static Customer create(AddressDto address, Person person) {
        return new Customer(address, person);
    }

    public ObjectId getId() {
        return id;
    }

    public AddressDto getAddress() {
        return address;
    }

    public Person getPerson() {
        return person;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public ZoneId getCreatedAtZone() {
        return createdAtZone;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
