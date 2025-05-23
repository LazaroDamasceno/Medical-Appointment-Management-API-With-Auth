package com.api.v2.people.services.exposed

import com.api.v2.people.domain.exposed.Person
import com.api.v2.people.requests.PersonUpdateDTO

interface PersonUpdateService {
    suspend fun update(person: Person, updatingDTO: PersonUpdateDTO): Person
}