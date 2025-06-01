package com.api.v2.doctors.utils

import com.api.v2.doctors.domain.DoctorCrudRepository
import com.api.v2.doctors.domain.Doctor
import com.api.v2.doctors.exceptions.DoctorNotFoundException
import org.springframework.stereotype.Component

@Component
class DoctorFinderImpl(private val crudRepository: DoctorCrudRepository) : DoctorFinder {

    override fun findByLicenseNumber(licenseNumber: String): Doctor {
        val foundDoctor = crudRepository.findByLicenseNumber(licenseNumber)
        if (foundDoctor == null) {
            throw DoctorNotFoundException(licenseNumber)
        }
        return foundDoctor
    }
}