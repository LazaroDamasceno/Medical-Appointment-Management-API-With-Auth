package com.api.v1.people;

public interface PersonUpdateService {
    Person update(Person person, PersonUpdateDTO updatingDto);
}
