package com.api.v2.doctors

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface DoctorCrudRepository: MongoRepository<Doctor, String> {

    @Query("{ 'licenseNumber': ?0 }")
    fun findByLicenseNumber(licenseNumber: String): Doctor?

    @Query("{ 'person.sin': ?0 }")
    fun findBySIN(sin: String): Doctor?

    @Query("{ 'person.email': ?0 }")
    fun findByEmail(email: String): Doctor?
}