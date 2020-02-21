package com.softseve.migration.reader;

import com.softseve.migration.model.Patient;
import com.softseve.migration.model.PatientContact;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface Reader {

    List<Patient> readPatients(List<Path> paths) throws IOException;
    List<PatientContact> readPatientsContacts(List<Path> paths) throws IOException;

}
