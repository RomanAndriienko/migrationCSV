package com.softseve.migration.processor;

import com.softseve.migration.converter.Converter;
import com.softseve.migration.loader.Loader;
import com.softseve.migration.model.Patient;
import com.softseve.migration.model.PatientContact;
import com.softseve.migration.model.PatientResult;
import com.softseve.migration.reader.Reader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@Setter
public class Processor {

    private Loader loader;
    private Reader reader;
    private Converter converter;
    private List<Patient> patients;
    private List<PatientContact> patientContacts;
    private List<PatientResult> dataToLoad;

    public Processor(Loader loader, Reader reader, Converter converter) {

        this.loader = loader;
        this.reader = reader;
        this.converter = converter;
        patientContacts = new ArrayList<>();
        patients = new ArrayList<>();
        dataToLoad = new ArrayList<>();
    }


    public void process(List<Path> paths) {

        patients = reader.readPatients(paths);
        patientContacts = reader.readPatientsContacts(paths);
        dataToLoad = converter.convert(patients, patientContacts);
        loader.load(dataToLoad);
    }
}
