package com.api.v2.doctors.services

import com.api.v2.common.DuplicatedLicenseNumberException
import com.api.v2.doctors.domain.DoctorCrudRepository
import com.api.v2.doctors.Doctor
import com.api.v2.doctors.DoctorResponseDTO
import com.api.v2.doctors.toDTO
import com.api.v2.people.PersonRegistrationDTO
import com.api.v2.people.exceptions.DuplicatedEmailException
import com.api.v2.people.exceptions.DuplicatedSINException
import com.api.v2.people.PersonRegistrationService
import jakarta.validation.Valid
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.net.URI

@Service
class DoctorRegistrationServiceImpl(
    val personRegistrationService: PersonRegistrationService,
    val crudRepository: DoctorCrudRepository
) : DoctorRegistrationService {

    override fun register(
        licenseNumber: String,
        personDTO: @Valid PersonRegistrationDTO
    ): ResponseEntity<DoctorResponseDTO> {
        validate(licenseNumber, personDTO)
        val savedPerson = personRegistrationService.register(personDTO)
        val newDoctor = Doctor.of(licenseNumber, savedPerson)
        val savedDoctor = crudRepository.save(newDoctor)
        val dto = savedDoctor.toDTO()
        return ResponseEntity
            .created(URI.create("/api/v2/doctors"))
            .contentType(MediaType.APPLICATION_JSON)
            .body(dto)
    }

    fun validate(licenseNumber: String, personDTO: PersonRegistrationDTO) {
        if (crudRepository.findByLicenseNumber(licenseNumber) != null) {
            throw DuplicatedLicenseNumberException()
        }
        if (crudRepository.findBySIN(personDTO.sin) != null) {
            throw DuplicatedSINException()
        }
        if (crudRepository.findByEmail(personDTO.email) != null) {
            throw DuplicatedEmailException()
        }
    }
}