package com.api.v2.customers.services

import com.api.v2.people.requests.PersonUpdateDTO
import org.springframework.http.ResponseEntity

interface CustomerUpdateService {
    suspend fun update(customerId: String, updateDTO: PersonUpdateDTO): ResponseEntity<Unit>
}