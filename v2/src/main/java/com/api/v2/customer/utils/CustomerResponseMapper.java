package com.api.v2.customer.utils;

import com.api.v2.customer.dtos.CustomerResponseDto;
import com.api.v2.customer.domain.Customer;
import com.api.v2.people.utils.PersonResponseMapper;
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
