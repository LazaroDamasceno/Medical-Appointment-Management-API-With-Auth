package com.api.v2.customers.domain

import com.api.v2.customers.domain.exposed.Customer
import org.springframework.data.annotation.Id
import java.time.LocalDateTime
import java.util.UUID

data class CustomerAuditTrail(
    @Id
    val id: String,
    val customer: Customer,
    val createdAt: LocalDateTime
) {

    companion object {
        fun of(customer: Customer): CustomerAuditTrail {
            return CustomerAuditTrail(
                UUID.randomUUID().toString(),
                customer,
                LocalDateTime.now()
            )
        }
    }
}
