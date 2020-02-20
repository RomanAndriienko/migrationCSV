package com.softseve.migration.converter;

import com.softseve.migration.model.Patient;
import com.softseve.migration.model.PatientContact;
import com.softseve.migration.model.PatientResult;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class Converter {

    public List<PatientResult> convert(List<Patient> patients,
        List<PatientContact> patientContacts) {
        return null;
    }
}
