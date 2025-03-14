package com.api.v2.customers.domain;

import com.api.v2.common.Address;
import com.api.v2.common.DstCheckerUtil;
import com.api.v2.people.domain.exposed.Person;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.UUID;

@Document
public class Customer {

    @Id
    private String id;
    private Address address;
    private Person person;
    private final LocalDateTime createdAt;
    private final ZoneId createdAtZoneId;
    private final ZoneOffset createdAtZoneOffset;
    private final boolean isCreatedDuringDST;

    private Customer(Address address, Person person) {
        this.id = UUID.randomUUID().toString();
        this.address = address;
        this.person = person;
        this.createdAt = LocalDateTime.now(ZoneId.systemDefault());
        this.createdAtZoneId = ZoneId.systemDefault();
        this.createdAtZoneOffset = OffsetDateTime.now().getOffset();
        this.isCreatedDuringDST = DstCheckerUtil.isGivenDateTimeFollowingDST(LocalDateTime.now(), ZoneId.systemDefault());
    }

    public static Customer of(Address address, Person person) {
        return new Customer(address, person);
    }

    public String getId() {
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

    public ZoneId getCreatedAtZoneId() {
        return createdAtZoneId;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public ZoneOffset getCreatedAtZoneOffset() {
        return createdAtZoneOffset;
    }

    public boolean isCreatedDuringDST() {
        return isCreatedDuringDST;
    }
}
