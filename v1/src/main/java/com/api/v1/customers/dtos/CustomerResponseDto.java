package com.api.v1.customers.dtos;

import com.api.v1.customers.domain.Customer;
import com.api.v1.people.utils.FullNameFormatter;

public record CustomerResponseDto(
        String id,
        String fullName
) {

    public static CustomerResponseDto from(Customer customer) {
        return new CustomerResponseDto(
                customer.getId(),
                FullNameFormatter.format(customer.getPerson())
        );
    }
}
