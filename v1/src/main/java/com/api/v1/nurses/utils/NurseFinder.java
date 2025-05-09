package com.api.v1.nurses.utils;

import com.api.v1.nurses.domain.NurseRepository;
import com.api.v1.nurses.domain.exposed.Nurse;
import com.api.v1.nurses.exceptions.NurseNotFoundException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public final class NurseFinder {

    private final NurseRepository nurseRepository;

    public NurseFinder(NurseRepository nurseRepository) {
        this.nurseRepository = nurseRepository;
    }
    public Mono<Nurse> findById(String nurseId) {
        return nurseRepository
                .findById(nurseId)
                .switchIfEmpty(Mono.error(new NurseNotFoundException()));
    }
}
