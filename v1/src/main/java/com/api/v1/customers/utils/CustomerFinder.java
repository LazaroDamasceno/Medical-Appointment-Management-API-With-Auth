package com.api.v1.customers.utils;

import com.api.v1.common.MongoId;
import com.api.v1.common.Result;
import com.api.v1.common.Result20X;
import com.api.v1.common.Result40X;
import com.api.v1.customers.domain.Customer;
import com.api.v1.customers.domain.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public final class CustomerFinder {

    private final CustomerRepository repository;

    public Result findById(@MongoId String id) {
        Optional<Customer> optional = repository.findById(id);
        if (optional.isEmpty()) {
            String message = "Customer whose id is %s was not found.".formatted(id);
            return Result40X.generate404(message);
        }
        Customer customer = optional.get();
        return Result20X.generate200(customer);
    }
}
