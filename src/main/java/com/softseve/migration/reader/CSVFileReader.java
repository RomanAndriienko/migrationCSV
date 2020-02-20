package com.softseve.migration.reader;

import com.softseve.migration.model.Patient;
import com.softseve.migration.model.PatientContact;
import java.nio.file.Path;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CSVFileReader implements Reader {

    @Override
    public List<Patient> readPatients(List<Path> path) {
        return null;
    }

    @Override
    public List<PatientContact> readPatientsContacts(List<Path> path) {
        return null;
    }
}
