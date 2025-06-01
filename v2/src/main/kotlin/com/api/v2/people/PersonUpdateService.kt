package com.api.v2.people

import com.api.v2.people.requests.PersonUpdateDTO

interface PersonUpdateService {
    fun update(person: Person, updatingDTO: PersonUpdateDTO): Person
}