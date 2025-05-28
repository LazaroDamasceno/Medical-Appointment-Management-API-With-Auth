package com.api.v2.doctors.services

import com.api.v2.doctors.domain.DoctorAuditRepository
import com.api.v2.doctors.domain.DoctorAuditTrail
import com.api.v2.doctors.domain.DoctorCrudRepository
import com.api.v2.doctors.exceptions.ActiveDoctorException
import com.api.v2.doctors.exceptions.TerminatedDoctorException
import com.api.v2.doctors.utils.exposed.DoctorFinder
import com.api.v2.people.requests.PersonUpdateDTO
import com.api.v2.people.services.exposed.PersonUpdateService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class DoctorManagementServiceImpl: DoctorManagementService {

    private val crudRepository: DoctorCrudRepository
    private val auditRepository: DoctorAuditRepository
    private val personUpdateService: PersonUpdateService
    private val doctorFinder: DoctorFinder

    constructor(
        crudRepository: DoctorCrudRepository,
        auditRepository: DoctorAuditRepository,
        personUpdateService: PersonUpdateService,
        doctorFinder: DoctorFinder
    ) {
        this.crudRepository = crudRepository
        this.auditRepository = auditRepository
        this.personUpdateService = personUpdateService
        this.doctorFinder = doctorFinder
    }

    override fun terminate(licenseNumber: String): ResponseEntity<Void> {
        val foundDoctor = doctorFinder.findByLicenseNumber(licenseNumber)
        if (crudRepository.findTerminatedByLicenseNumber(licenseNumber) != null) {
            throw TerminatedDoctorException()
        }
        val auditTrail = DoctorAuditTrail.of(foundDoctor)
        val savedAuditTrail = auditRepository.save(auditTrail)
        foundDoctor.markAsTerminated()
        val updatedDoctor = crudRepository.save(foundDoctor)
        return ResponseEntity.noContent().build()
    }

    override fun rehire(licenseNumber: String): ResponseEntity<Void> {
        val foundDoctor = doctorFinder.findByLicenseNumber(licenseNumber)
        if (crudRepository.findActiveByLicenseNumber(licenseNumber) != null) {
            throw ActiveDoctorException()
        }
        val auditTrail = DoctorAuditTrail.of(foundDoctor)
        val savedAuditTrail = auditRepository.save(auditTrail)
        foundDoctor.markAsRehired()
        val updatedDoctor = crudRepository.save(foundDoctor)
        return ResponseEntity.noContent().build()
    }

    override fun update(licenseNumber: String, updateDTO: @Valid PersonUpdateDTO): ResponseEntity<Void> {
        val foundDoctor = doctorFinder.findByLicenseNumber(licenseNumber)
        val auditTrail = DoctorAuditTrail.of(foundDoctor)
        val savedAuditTrail = auditRepository.save(auditTrail)
        val updatedPerson = personUpdateService.update(foundDoctor.person, updateDTO)
        foundDoctor.update(updatedPerson)
        val updatedDoctor = crudRepository.save(foundDoctor)
        return ResponseEntity.noContent().build()
    }

}