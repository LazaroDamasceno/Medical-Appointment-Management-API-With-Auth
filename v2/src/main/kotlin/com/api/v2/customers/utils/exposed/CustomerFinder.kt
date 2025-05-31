package com.api.v2.customers.utils.exposed

import com.api.v2.customers.Customer

interface CustomerFinder {
    fun findById(id: String): Customer
}