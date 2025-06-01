package com.api.v1.customers.utils;

import com.api.v1.customers.domain.Customer;

public interface CustomerFinder {
    Customer findById(String id);
}
