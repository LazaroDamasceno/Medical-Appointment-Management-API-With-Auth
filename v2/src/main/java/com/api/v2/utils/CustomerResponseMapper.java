package com.api.v2.utils;

import com.api.v2.domain.Customer;
import com.api.v2.dtos.CustomerResponseDto;

public class CustomerResponseMapper {

    public static CustomerResponseDto map(Customer customer) {
        return new CustomerResponseDto(
                PersonResponseMapper.map(customer.getPerson()),
                customer.getAddress()
        );
    }
}
