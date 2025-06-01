package com.api.v2.doctors.services

import com.api.v2.common.DuplicatedLicenseNumberException
import com.api.v2.doctors.domain.DoctorCrudRepository
import com.api.v2.doctors.Doctor
import com.api.v2.doctors.requests.DoctorRegistrationDTO
import com.api.v2.doctors.DoctorResponseDTO
import com.api.v2.doctors.toDTO
import com.api.v2.people.DuplicatedEmailException
import com.api.v2.people.DuplicatedSINException
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

    override fun register(registrationDTO: @Valid DoctorRegistrationDTO): ResponseEntity<DoctorResponseDTO> {
        validate(registrationDTO)
        val savedPerson = personRegistrationService.register(registrationDTO.person)
        val newDoctor = Doctor.of(registrationDTO.licenseNumber, savedPerson)
        val savedDoctor = crudRepository.save(newDoctor)
        val dto = savedDoctor.toDTO()
        return ResponseEntity
            .created(URI.create("/api/v2/doctors"))
            .contentType(MediaType.APPLICATION_JSON)
            .body(dto)
    }

    fun validate(registrationDTO: DoctorRegistrationDTO) {
        if (crudRepository.findByLicenseNumber(registrationDTO.licenseNumber) != null) {
            throw DuplicatedLicenseNumberException()
        }
        if (crudRepository.findBySIN(registrationDTO.person.sin) != null) {
            throw DuplicatedSINException()
        }
        if (crudRepository.findByEmail(registrationDTO.person.email) != null) {
            throw DuplicatedEmailException()
        }
    }
}