package com.api.v2.customers

class CustomerNotFoundException(id: String)
    : RuntimeException("Customer whose id is $id was not found.")