package com.api.v2.customers.utils.exposed

import com.api.v2.customers.domain.exposed.Customer

interface CustomerFinder {
    fun findById(id: String): Customer
}