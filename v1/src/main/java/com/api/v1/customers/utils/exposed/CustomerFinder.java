package com.api.v1.customers.utils.exposed;

import com.api.v1.customers.domain.Customer;

public interface CustomerFinder {
    Customer findById(String id);
}
