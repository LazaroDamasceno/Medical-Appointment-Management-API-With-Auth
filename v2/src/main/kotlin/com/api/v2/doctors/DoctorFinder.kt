package com.api.v2.doctors

interface DoctorFinder {
    fun findByLicenseNumber(licenseNumber: String): Doctor
}