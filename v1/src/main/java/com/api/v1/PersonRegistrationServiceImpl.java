package com.api.v1;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonRegistrationServiceImpl implements PersonRegistrationService {

    private final PersonRepository personRepository;

    @Override
    public Person register(@Valid PersonRegistrationDto dto) {
        isSinDuplicated(dto.sin());
        isEmailDuplicated(dto.email());
        Person newPerson = Person.of(dto);
        return personRepository.save(newPerson);
    }

    private void isSinDuplicated(String sin){
        if (personRepository.findBySin(sin).isPresent()) {
            throw new DuplicatedSinException();
        }
    }

    private void isEmailDuplicated(String email){
        if (personRepository.findByEmail(email).isPresent()) {
            throw new DuplicatedEmailException();
        }
    }
}
