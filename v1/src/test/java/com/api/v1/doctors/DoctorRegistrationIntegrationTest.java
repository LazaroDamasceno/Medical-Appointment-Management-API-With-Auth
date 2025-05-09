package com.api.v1.doctors;

import com.api.v1.common.States;
import com.api.v1.doctors.domain.MedicalLicenseNumber;
import com.api.v1.doctors.requests.DoctorRegistrationDto;
import com.api.v1.people.dtos.Address;
import com.api.v1.people.enums.Gender;
import com.api.v1.people.requests.PersonRegistrationDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DoctorRegistrationIntegrationTest {

	@Autowired
	WebTestClient webTestClient;

	DoctorRegistrationDto registrationDto = new DoctorRegistrationDto(
		new PersonRegistrationDto(
				"Christian",
				"",
				"Sewer",
				"987654321",
				LocalDate.of(2000,12,12),
				"1234567890",
				Gender.CIS_MALE,
				"chrissewer@mail.com",
				new Address(
						"Downtown",
						"Los Angeles",
						States.CA,
						"90012"
				)
		),
		new MedicalLicenseNumber(
				"12345678",
				States.CA
		)
	);

	@Test
	@Order(1)
	void testSuccessfulRegistration() {
		webTestClient
				.post()
				.uri("api/v1/doctors")
				.bodyValue(registrationDto)
				.exchange()
				.expectStatus()
				.is2xxSuccessful();
	}

	DoctorRegistrationDto registrationDtoWithDuplicatedSSn = new DoctorRegistrationDto(
			new PersonRegistrationDto(
					"Christian",
					"",
					"Sewer",
					"987654321",
					LocalDate.of(2000,12,12),
					"1234567890",
					Gender.CIS_MALE,
					"chrissewer@mail.com",
					new Address(
							"Downtown",
							"Los Angeles",
							States.CA,
							"90012"
					)
			),
			new MedicalLicenseNumber(
					"12345678",
					States.CA
			)
	);

	@Test
	@Order(2)
	void testUnsuccessfulRegistration_Duplicated_Ssn() {
		webTestClient
				.post()
				.uri("api/v1/doctors")
				.bodyValue(registrationDtoWithDuplicatedSSn)
				.exchange()
				.expectStatus()
				.is4xxClientError();
	}

	DoctorRegistrationDto registrationDtoWithDuplicatedEmail = new DoctorRegistrationDto(
			new PersonRegistrationDto(
					"Christian",
					"",
					"Sewer",
					"234156789",
					LocalDate.of(2000,12,12),
					"1234567890",
					Gender.CIS_MALE,
					"chrissewer@mail.com",
					new Address(
							"Downtown",
							"Los Angeles",
							States.CA,
							"90012"
					)
			),
			new MedicalLicenseNumber(
					"12345678",
					States.CA
			)
	);

	@Test
	@Order(3)
	void testUnsuccessfulRegistration_Duplicated_Email() {
		webTestClient
				.post()
				.uri("api/v1/doctors")
				.bodyValue(registrationDtoWithDuplicatedEmail)
				.exchange()
				.expectStatus()
				.is4xxClientError();
	}

	DoctorRegistrationDto registrationDtoWithDuplicatedMedicalLicenseNumber = new DoctorRegistrationDto(
			new PersonRegistrationDto(
					"Christian",
					"",
					"Sewer",
					"234156789",
					LocalDate.of(2000,12,12),
					"1234567890",
					Gender.CIS_MALE,
					"chrissewer@chrissewer.com",
					new Address(
							"Downtown",
							"Los Angeles",
							States.CA,
							"90012"
					)
			),
			new MedicalLicenseNumber(
					"12345678",
					States.CA
			)
	);

	@Test
	@Order(4)
	void testUnsuccessfulRegistration_Duplicated_Medical_License_Number() {
		webTestClient
				.post()
				.uri("api/v1/doctors")
				.bodyValue(registrationDtoWithDuplicatedMedicalLicenseNumber)
				.exchange()
				.expectStatus()
				.is4xxClientError();
	}
}
