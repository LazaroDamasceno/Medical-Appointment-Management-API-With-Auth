package com.api.v2.customers.domain

import com.api.v2.customers.Customer
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.UUID

@Document(collection = "CustomerAuditTrail")
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
