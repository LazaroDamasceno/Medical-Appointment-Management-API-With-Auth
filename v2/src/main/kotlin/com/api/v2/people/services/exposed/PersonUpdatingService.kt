package com.api.v2.people.services.exposed

import com.api.v2.people.domain.exposed.Person
import com.api.v2.people.requests.PersonUpdatingDTO

interface PersonUpdatingService {
    suspend fun update(person: Person, updatingDTO: PersonUpdatingDTO): Person
}