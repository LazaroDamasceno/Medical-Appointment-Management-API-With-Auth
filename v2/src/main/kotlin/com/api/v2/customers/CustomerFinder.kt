package com.api.v2.customers

import com.api.v2.customers.Customer

interface CustomerFinder {
    fun findById(id: String): Customer
}