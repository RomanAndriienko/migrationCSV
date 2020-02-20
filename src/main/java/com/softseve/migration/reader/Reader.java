package com.softseve.migration.reader;

import com.softseve.migration.model.Patient;
import com.softseve.migration.model.PatientContact;
import java.nio.file.Path;
import java.util.List;

public interface Reader {

    List<Patient> readPatients(List<Path> path);
    List<PatientContact> readPatientsContacts(List<Path> path);

}
