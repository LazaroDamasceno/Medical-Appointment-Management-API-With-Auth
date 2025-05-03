package com.api.v1.customers.domain.exposed;

import com.api.v1.people.dtos.Address;
import com.api.v1.customers.responses.CustomerResponseDto;
import com.api.v1.people.domain.exposed.Person;
import com.api.v1.people.utils.FullNameFormatter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document
public record Customer(
        @Id
        String id,
        Person person,
        LocalDateTime createdAt
) {

        public static Customer of(Person person) {
                return new Customer(
                        UUID.randomUUID().toString(),
                        person,
                        LocalDateTime.now()
                );
        }

        public CustomerResponseDto toDto() {
                return new CustomerResponseDto(
                        id,
                        FullNameFormatter.format(person)
                );
        }

}
