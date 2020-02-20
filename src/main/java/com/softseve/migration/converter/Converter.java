package com.softseve.migration.converter;

import com.softseve.migration.errors.FailMessage;
import com.softseve.migration.model.Patient;
import com.softseve.migration.model.PatientContact;
import com.softseve.migration.model.PatientResult;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class Converter {

    private final static Logger logger = LoggerFactory.getLogger(Converter.class);

    public List<PatientResult> convert(List<Patient> patients,
        List<PatientContact> patientContacts) {
        List<PatientResult> results = new ArrayList<>();
        List<FailMessage> errors = new ArrayList<>();

        for (Patient patient : patients) {
            for (PatientContact contact : patientContacts) {
                if (patient.getId().equals(contact.getId())) {
                    results.add(_convert(patient, contact));
                } else {
                    //TODO
                    logger.error(String.format("no matches by %s in line %s ", patient.getId(),
                        patient.getPatientSrc().getLineNumber()));
                    errors.add(new FailMessage(patient.getPatientSrc().getLineNumber(), "msg"));
                }
            }

        }
        return results;
    }

    private PatientResult _convert(Patient patient, PatientContact contact) {
        return PatientResult.builder()
            .accessDate(patient.getAccessDate())
            .bDate(patient.getBDate())
            .cContactDateTime(contact.getCContactDateTime())
            .cDate(contact.getCDate())
            .cntRef(contact.getCntRef())
            .cntRef2(contact.getCntRef2())
            .contactRef(contact.getContactRef())
            .contactType(contact.getContactType())
            .contactTypeId(contact.getContactTypeId())
            .contactTypeIdx(contact.getContactTypeIdx())
            .cPatientDateTime(patient.getCPatientDateTime())
            .facility(contact.getFacility())
            .firstName(contact.getFirstName())
            .lastName(contact.getLastName())
            .id(patient.getId())
            .items(patient.getItems())
            .MPI(patient.getMPI())
            .patientTypeId(patient.getPatientTypeId())
            .patientTypeRef(patient.getPatientTypeRef())
            .patientTypeTxt(patient.getPatientTypeTxt())
            .pCode(contact.getPCode())
            .refId(patient.getRefId())
            .sums(contact.getSums())
            .uContactDateTime(contact.getUContactDateTime())
            .uPatientDateTime(patient.getUPatientDateTime())
            .user(contact.getUser())
            .contactSrc(contact.getContactSrc())
            .patientSrc(patient.getPatientSrc())
            .build();
    }
}
