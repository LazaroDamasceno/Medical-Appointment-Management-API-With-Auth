package com.api.v2.utils;

import com.api.v2.domain.Customer;
import com.api.v2.dtos.CustomerResponseDto;
import reactor.core.publisher.Mono;

public class CustomerResponseMapper {

    public static CustomerResponseDto mapToDto(Customer customer) {
        return new CustomerResponseDto(
                PersonResponseMapper.map(customer.getPerson()),
                customer.getAddress()
        );
    }

    public static Mono<CustomerResponseDto> mapToMono(Customer customer) {
        return Mono.just(mapToDto(customer));
    }
}
