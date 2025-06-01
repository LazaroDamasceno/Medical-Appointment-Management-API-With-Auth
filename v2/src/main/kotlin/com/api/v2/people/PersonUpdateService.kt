package com.api.v2.people

interface PersonUpdateService {
    fun update(person: Person, updatingDTO: PersonUpdateDTO): Person
}