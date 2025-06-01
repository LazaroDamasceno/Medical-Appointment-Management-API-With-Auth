package com.api.v2.doctors.utils

import com.api.v2.doctors.domain.DoctorCrudRepository
import com.api.v2.doctors.Doctor
import com.api.v2.doctors.DoctorFinder
import com.api.v2.doctors.DoctorNotFoundException
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