package com.api.v2.doctors

import com.api.v2.doctors.Doctor

interface DoctorFinder {
    fun findByLicenseNumber(licenseNumber: String): Doctor
}