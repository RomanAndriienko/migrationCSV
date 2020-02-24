package com.softseve.migration.converter;

import com.softseve.migration.model.Patient;
import com.softseve.migration.model.PatientContact;
import com.softseve.migration.model.PatientResult;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class Converter {

    public List<PatientResult> convert(List<Patient> patients,
        List<PatientContact> patientContacts) {

        List<PatientResult> results = new ArrayList<>();
        List<PatientResult> temps = new ArrayList<>();

        for (PatientContact contact : patientContacts) {
            results.add(addContactToResult(contact));
        }

        for (Patient patient : patients) {
            for (PatientResult result : results) {
                if (result.getId().equals(patient.getId())) {
                    setPatientToResult(result, patient);
                }
            }
            temps.add(addPatientToResult(patient));
        }

        results.addAll(temps);

        return results;
    }

    private PatientResult addContactToResult(PatientContact contact) {
        return PatientResult.builder()
            .id(contact.getId())
            .cContactDateTime(contact.getCContactDateTime())
            .cDate(contact.getCDate())
            .cntRef(contact.getCntRef())
            .cntRef2(contact.getCntRef2())
            .contactRef(contact.getContactRef())
            .contactType(contact.getContactType())
            .contactTypeId(contact.getContactTypeId())
            .contactTypeIdx(contact.getContactTypeIdx())
            .facility(contact.getFacility())
            .firstName(contact.getFirstName())
            .lastName(contact.getLastName())
            .pCode(contact.getPCode())
            .sums(contact.getSums())
            .uContactDateTime(contact.getUContactDateTime())
            .user(contact.getUser())
            .contactSrc(contact.getContactSrc())
            .build();
    }

    private PatientResult addPatientToResult(Patient patient) {
        return PatientResult.builder()
            .accessDate(patient.getAccessDate())
            .bDate(patient.getBDate())
            .cPatientDateTime(patient.getCPatientDateTime())
            .id(patient.getId())
            .items(patient.getItems())
            .MPI(patient.getMPI())
            .patientTypeId(patient.getPatientTypeId())
            .patientTypeRef(patient.getPatientTypeRef())
            .patientTypeTxt(patient.getPatientTypeTxt())
            .refId(patient.getRefId())
            .uPatientDateTime(patient.getUPatientDateTime())
            .patientSrc(patient.getPatientSrc())
            .build();
    }

    private void setPatientToResult(PatientResult result, Patient patient) {
        result.setAccessDate(patient.getAccessDate());
        result.setBDate(patient.getBDate());
        result.setCPatientDateTime(patient.getCPatientDateTime());
        result.setItems(patient.getItems());
        result.setMPI(patient.getMPI());
        result.setPatientTypeId(patient.getPatientTypeId());
        result.setPatientTypeRef(patient.getPatientTypeRef());
        result.setPatientTypeTxt(patient.getPatientTypeTxt());
        result.setRefId(patient.getRefId());
        result.setUPatientDateTime(patient.getUPatientDateTime());
        result.setPatientSrc(patient.getPatientSrc());
    }
}
