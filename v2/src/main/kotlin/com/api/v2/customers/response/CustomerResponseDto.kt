package com.api.v2.customers.response

import com.api.v2.common.fullName
import com.api.v2.customers.domain.Customer

data class CustomerResponseDto(
    val id: String,
    val fullName: String
) {

    companion object {
        fun from(customer: Customer): CustomerResponseDto {
            return CustomerResponseDto(customer.id, customer.person.fullName())
        }
    }
}
