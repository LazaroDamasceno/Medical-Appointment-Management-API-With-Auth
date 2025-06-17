package com.api.v1.medical_appointments.utils;

import com.api.v1.common.ObjectId;
import com.api.v1.customers.Customer;
import com.api.v1.doctors.Doctor;
import com.api.v1.medical_appointments.MedicalAppointment;
import com.api.v1.medical_appointments.MedicalAppointmentFinder;
import com.api.v1.medical_appointments.domain.MedicalAppointmentCrudRepository;
import com.api.v1.medical_appointments.exceptions.InaccessibleMedicalAppointmentException;
import com.api.v1.medical_appointments.exceptions.MedicalAppointmentNotFoundException;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MedicalAppointmentFinderImpl implements MedicalAppointmentFinder {

    private final MedicalAppointmentCrudRepository crudRepository;

    public MedicalAppointmentFinderImpl(MedicalAppointmentCrudRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    public MedicalAppointment findById(@ObjectId String id) {
        Optional<MedicalAppointment> foundAppointment = crudRepository.findById(id);
        return foundAppointment.orElseThrow(() -> new MedicalAppointmentNotFoundException(id));
    }

    @Override
    public MedicalAppointment findByIdAndCustomer(@ObjectId String id, @NotNull Customer customer) {
        MedicalAppointment foundAppointment = findById(id);
        if (foundAppointment.customer().id().equals(customer.id())) {
            String message = """
                    Medical appointment whose id is %s cannot be accessed by customer whose id is %s.
                    """.formatted(id, customer.id());
            throw new InaccessibleMedicalAppointmentException(message);
        }
        return foundAppointment;
    }

    @Override
    public MedicalAppointment findByIdAndDoctor(@ObjectId String id, @NotNull Doctor doctor) {
        MedicalAppointment foundAppointment = findById(id);
        if (foundAppointment.doctor().id().equals(doctor.id())) {
            String message = """
                    Medical appointment whose id is %s cannot be accessed by doctor whose medical license number is %s.
                    """.formatted(id, doctor.licenseNumber());
            throw new InaccessibleMedicalAppointmentException(message);
        }
        return foundAppointment;
    }
}
