package com.softseve.migration.converter;

import com.softseve.migration.model.Patient;
import com.softseve.migration.model.PatientContact;
import com.softseve.migration.model.PatientResult;
import com.softseve.migration.model.Source;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class ConverterTest {

    private final UUID uuid = UUID.randomUUID();
    private Converter converter;
    private List<Patient> patients;
    private List<PatientContact> contacts;
    private Patient patient;
    private PatientContact patientContact;

    @BeforeEach
    public void setUp() {
        converter = new Converter();
        patients = new ArrayList<>();
        contacts = new ArrayList<>();
        patient = Patient.builder()
            .accessDate("String")
            .bDate("String")
            .cPatientDateTime("String")
            .id(uuid)
            .items(1)
            .MPI(1)
            .patientTypeId("String")
            .patientTypeRef(UUID.randomUUID())
            .patientTypeTxt("String")
            .refId("String")
            .uPatientDateTime("String")
            .patientSrc(new Source())
            .build();

        patientContact = PatientContact.builder()
            .id(uuid)
            .cContactDateTime("String")
            .cDate("String")
            .cntRef(1)
            .cntRef2(1)
            .contactRef("String")
            .contactType("String")
            .contactTypeId("String")
            .contactTypeIdx(UUID.randomUUID())
            .facility(1)
            .firstName("String")
            .lastName("String")
            .pCode("String")
            .sums(1)
            .uContactDateTime("String")
            .user("String")
            .contactSrc(new Source())
            .build();
    }

    @Test
    public void convert() {
        patients.add(patient);
        contacts.add(patientContact);

        List<PatientResult> results = converter.convert(patients, contacts);

        System.out.println(results);

    }
}