package com.api.v2.utils;

import com.api.v2.domain.CustomerRepository;
import com.api.v2.exceptions.DuplicatedEmailException;
import com.api.v2.exceptions.DuplicatedSsnException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class PersonDuplicatedDataCheckUtil {

    private final CustomerRepository customerRepository;

    public PersonDuplicatedDataCheckUtil(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Mono<Void> onDuplicatedSsn(String ssn) {
        return customerRepository
                .findAll()
                .filter(c -> c.getPerson().getSsn().equals(ssn))
                .hasElements()
                .flatMap(exists -> {
                    if (exists) return Mono.error(DuplicatedSsnException::new);
                    return Mono.empty();
                });
    }

    public Mono<Void>  onDuplicatedEmail(String email) {
        return customerRepository
                .findAll()
                .filter(c -> c.getPerson().getSsn().equals(email))
                .hasElements()
                .flatMap(exists -> {
                    if (exists) return Mono.error(DuplicatedEmailException::new);
                    return Mono.empty();
                });
    }
}
