package com.api.v2.customers.utils.exposed

import com.api.v2.customers.domain.Customer

interface CustomerFinder {
    suspend fun findById(id: String): Customer
}