package com.api.v2.doctors.services

import com.api.v2.doctors.domain.DoctorAuditRepository
import com.api.v2.doctors.domain.DoctorAuditTrail
import com.api.v2.doctors.domain.DoctorCrudRepository
import com.api.v2.doctors.exceptions.ActiveDoctorException
import com.api.v2.doctors.exceptions.TerminatedDoctorException
import com.api.v2.doctors.DoctorFinder
import com.api.v2.people.PersonUpdateDTO
import com.api.v2.people.PersonUpdateService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class DoctorManagementServiceImpl(
    val crudRepository: DoctorCrudRepository,
    val auditRepository: DoctorAuditRepository,
    val personUpdateService: PersonUpdateService,
    val doctorFinder: DoctorFinder
) : DoctorManagementService {

    override fun terminate(licenseNumber: String): ResponseEntity<Void> {
        val foundDoctor = doctorFinder.findByLicenseNumber(licenseNumber)
        if (crudRepository.findTerminatedByLicenseNumber(licenseNumber) != null) {
            throw TerminatedDoctorException()
        }
        val auditTrail = DoctorAuditTrail.of(foundDoctor)
        val savedAuditTrail = auditRepository.save(auditTrail)
        val updatedDoctor = foundDoctor.markAsTerminated()
        val savedDoctor = crudRepository.save(updatedDoctor)
        return ResponseEntity.noContent().build()
    }

    override fun rehire(licenseNumber: String): ResponseEntity<Void> {
        val foundDoctor = doctorFinder.findByLicenseNumber(licenseNumber)
        if (crudRepository.findActiveByLicenseNumber(licenseNumber) != null) {
            throw ActiveDoctorException()
        }
        val auditTrail = DoctorAuditTrail.of(foundDoctor)
        val savedAuditTrail = auditRepository.save(auditTrail)
        val updatedDoctor =foundDoctor.markAsRehired()
        val savedDoctor = crudRepository.save(updatedDoctor)
        return ResponseEntity.noContent().build()
    }

    override fun update(licenseNumber: String, updateDTO: @Valid PersonUpdateDTO): ResponseEntity<Void> {
        val foundDoctor = doctorFinder.findByLicenseNumber(licenseNumber)
        val auditTrail = DoctorAuditTrail.of(foundDoctor)
        val savedAuditTrail = auditRepository.save(auditTrail)
        val updatedPerson = personUpdateService.update(foundDoctor.person, updateDTO)
        val updatedDoctor = foundDoctor.update(updatedPerson)
        val savedDoctor = crudRepository.save(updatedDoctor)
        return ResponseEntity.noContent().build()
    }

}