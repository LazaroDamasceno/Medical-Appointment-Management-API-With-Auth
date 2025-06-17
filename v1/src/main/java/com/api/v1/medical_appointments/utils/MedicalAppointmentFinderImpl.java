package com.api.v1.medical_appointments.utils;

import com.api.v1.common.ObjectId;
import com.api.v1.customers.Customer;
import com.api.v1.customers.CustomerFinder;
import com.api.v1.medical_appointments.MedicalAppointment;
import com.api.v1.medical_appointments.MedicalAppointmentFinder;
import com.api.v1.medical_appointments.domain.MedicalAppointmentCrudRepository;
import com.api.v1.medical_appointments.exceptions.InaccessibleMedicalAppointmentException;
import com.api.v1.medical_appointments.exceptions.MedicalAppointmentNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MedicalAppointmentFinderImpl implements MedicalAppointmentFinder {

    private final MedicalAppointmentCrudRepository crudRepository;
    private final CustomerFinder customerFinder;

    public MedicalAppointmentFinderImpl(
            MedicalAppointmentCrudRepository crudRepository,
            CustomerFinder customerFinder
    ) {
        this.crudRepository = crudRepository;
        this.customerFinder = customerFinder;
    }

    @Override
    public MedicalAppointment findById(@ObjectId String id) {
        Optional<MedicalAppointment> foundAppointment = crudRepository.findById(id);
        return foundAppointment.orElseThrow(() -> new MedicalAppointmentNotFoundException(id));
    }

    @Override
    public MedicalAppointment findByIdAndCustomer(@ObjectId String id, @ObjectId String customerId) {
        Customer foundCustomer = customerFinder.findById(customerId);
        MedicalAppointment foundAppointment = findById(id);
        if (foundAppointment.customer().id().equals(foundCustomer.id())) {
            String message = """
                    Medical appointment whose id is %s cannot be accessed by customer who id is %s.
                    """.formatted(id, customerId);
            throw new InaccessibleMedicalAppointmentException(message);
        }
        return foundAppointment;
    }
}
