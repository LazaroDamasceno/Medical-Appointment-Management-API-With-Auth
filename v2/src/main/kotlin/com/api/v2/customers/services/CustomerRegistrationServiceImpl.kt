package com.api.v2.customers.services

import com.api.v2.common.ErrorMessages
import com.api.v2.common.ResultData
import com.api.v2.common.StatusCode
import com.api.v2.customers.domain.Customer
import com.api.v2.customers.domain.CustomerRepository
import com.api.v2.customers.response.CustomerResponseDto
import com.api.v2.people.requests.PersonRegistrationDto
import com.api.v2.people.services.PersonRegistrationService
import jakarta.validation.Valid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.net.URI

@Service
class CustomerRegistrationServiceImpl(
    private val repository: CustomerRepository,
    private val personRegistrationService: PersonRegistrationService
): CustomerRegistrationService {

    override suspend fun register(registrationDto: @Valid PersonRegistrationDto): ResponseEntity<ResultData<CustomerResponseDto>> {
        return withContext(Dispatchers.IO) {
            val foundCustomerBySin = repository.findBySin(registrationDto.sin)
            if (foundCustomerBySin != null) {
                val error = ResultData.error<CustomerResponseDto>(ErrorMessages.DUPLICATED_SIN.value)
                return@withContext ResponseEntity.status(StatusCode.CONFLICT.value).body(error)
            }
            val foundCustomerByEmail = repository.findByEmail(registrationDto.email)
            if (foundCustomerByEmail != null) {
                val error = ResultData.error<CustomerResponseDto>(ErrorMessages.DUPLICATED_EMAIL.value)
                return@withContext ResponseEntity.status(StatusCode.CONFLICT.value).body(error)
            }
            val savedPerson = personRegistrationService.register(registrationDto)
            val newCustomer = Customer.of(savedPerson)
            val savedCustomer = repository.save(newCustomer)
            val dto = savedCustomer.toDto()
            val result = ResultData.created(dto)
            ResponseEntity.created(URI.create("/api/v2/customers")).body(result)
        }
    }
}