package com.api.v1.customers.response;

import com.api.v1.customers.domain.Customer;
import com.api.v1.people.utils.FullNameFormatter;
import jakarta.validation.constraints.NotNull;
import org.springframework.hateoas.RepresentationModel;

public final class CustomerResponseDTO extends RepresentationModel<CustomerResponseDTO> {

    private String id;
    private String fullName;

    private CustomerResponseDTO() {
    }

    private CustomerResponseDTO(String id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    public static CustomerResponseDTO from(@NotNull Customer customer) {
        return new CustomerResponseDTO(
                customer.getId(),
                FullNameFormatter.format(customer.getPerson())
        );
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }
}
