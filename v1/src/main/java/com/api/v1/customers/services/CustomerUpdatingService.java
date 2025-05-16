package com.api.v1.customers.services;

import com.api.v1.common.Result;
import com.api.v1.customers.response.CustomerResponseDto;
import com.api.v1.people.requests.PersonUpdatingDto;
import org.springframework.http.ResponseEntity;

public interface CustomerUpdatingService {
    ResponseEntity<Result<CustomerResponseDto>> update(String customerId, PersonUpdatingDto personUpdatingDto);
}
