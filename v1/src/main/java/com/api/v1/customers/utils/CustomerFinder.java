package com.api.v1.customers.utils;

import com.api.v1.common.*;
import com.api.v1.customers.domain.Customer;
import com.api.v1.customers.domain.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomerFinder {

    private final CustomerRepository repository;

    public Result<Customer> findById(@ObjectId String id) {
        Optional<Customer> optional = repository.findById(id);
        if (optional.isEmpty()) {
            String message = ErrorMessages.customerNotFoundMessage(id);
            return Result.generate404(message);
        }
        return Result.generate200(optional.get());
    }
}
