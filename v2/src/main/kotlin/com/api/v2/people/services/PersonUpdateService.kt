package com.api.v2.people.services

import com.api.v2.people.domain.Person
import com.api.v2.people.requests.PersonUpdateDTO

interface PersonUpdateService {
    fun update(person: Person, updatingDTO: PersonUpdateDTO): Person
}