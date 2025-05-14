package com.api.v2.people.services

import com.api.v2.people.domain.Person
import com.api.v2.people.requests.PersonUpdatingDto

interface PersonUpdateService {
    suspend fun update(person: Person, updatingDto: PersonUpdatingDto): Person
}