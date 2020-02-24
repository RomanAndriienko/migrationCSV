package com.softseve.migration.processor;

import com.softseve.migration.converter.Converter;
import com.softseve.migration.loader.PatientLoader;
import com.softseve.migration.loader.SourceLoader;
import com.softseve.migration.model.Patient;
import com.softseve.migration.model.PatientContact;
import com.softseve.migration.model.PatientResult;
import com.softseve.migration.reader.Reader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@Setter
public class Processor {

    private PatientLoader patientLoader;
    private SourceLoader sourceLoader;
    private Reader reader;
    private Converter converter;
    private Map<UUID, Patient> patients;
    private Map<UUID , PatientContact> patientContacts;
    private List<PatientResult> dataToLoad;

    public Processor(PatientLoader patientLoader, SourceLoader sourceLoader, Reader reader, Converter converter) {

        this.patientLoader = patientLoader;
        this.sourceLoader = sourceLoader;
        this.reader = reader;
        this.converter = converter;
        patientContacts = new HashMap<>();
        patients = new HashMap<>();
        dataToLoad = new ArrayList<>();
    }


    public void process(List<Path> paths) throws IOException {

        patients = reader.readPatients(paths);
        patientContacts = reader.readPatientsContacts(paths);
        dataToLoad = converter.convert(patients, patientContacts);
        patientLoader.load(dataToLoad);
        sourceLoader.loadContactInfo(dataToLoad);
        sourceLoader.loadPatientInfo(dataToLoad);
    }
}
