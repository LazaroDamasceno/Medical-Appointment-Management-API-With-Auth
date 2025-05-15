package com.api.v1.customers.response;

import com.api.v1.customers.domain.Customer;
import com.api.v1.people.utils.FullNameFormatter;
import jakarta.validation.constraints.NotNull;
import org.springframework.hateoas.RepresentationModel;

public final class CustomerResponseDto extends RepresentationModel<CustomerResponseDto> {

    private String id;
    private String fullName;

    private CustomerResponseDto() {
    }

    private CustomerResponseDto(String id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    public static CustomerResponseDto from(@NotNull Customer customer) {
        return new CustomerResponseDto(
                customer.id(),
                FullNameFormatter.format(customer.person())
        );
    }

    public String id() {
        return id;
    }

    public String fullName() {
        return fullName;
    }
}
