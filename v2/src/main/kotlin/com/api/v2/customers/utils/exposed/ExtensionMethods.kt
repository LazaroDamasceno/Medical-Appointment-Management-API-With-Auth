package com.api.v2.customers.utils.exposed

import com.api.v2.customers.domain.exposed.Customer
import com.api.v2.customers.responses.CustomerResponseDTO

fun Customer.toDTO(): CustomerResponseDTO {
    return CustomerResponseDTO.from(this)
}