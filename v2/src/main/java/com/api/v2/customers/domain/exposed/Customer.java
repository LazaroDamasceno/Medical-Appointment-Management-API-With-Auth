package com.api.v2.customers.domain.exposed;

import com.api.v2.common.Address;
import com.api.v2.people.domain.exposed.Person;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document
public record Customer(
        @Id
        String id,
        Address address,
        Person person,
        LocalDateTime createdAt
) {

        public static Customer of(Address address, Person person) {
                return new Customer(
                        UUID.randomUUID().toString(),
                        address,
                        person,
                        LocalDateTime.now()
                );
        }

}
