package com.softseve.migration.reader;

import com.softseve.migration.model.Patient;
import com.softseve.migration.model.PatientContact;
import com.softseve.migration.model.Source;
import com.softseve.migration.validator.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

@Service
public class CSVFileReader implements Reader {


    CSVParser CSVparser;
    Validator validator = new Validator();

    @Override
    public List<Patient> readPatients(List<Path> paths) throws IOException {
        List<Patient> patients = new ArrayList<>();
        Path patientsPath = getPatientPath(paths);
        try (
            java.io.Reader reader =
                Files.newBufferedReader(patientsPath);
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                .withAllowDuplicateHeaderNames()
                .withAllowMissingColumnNames()
                .withFirstRecordAsHeader()
                .withIgnoreHeaderCase());
        ) {
            for (CSVRecord csvRecord : csvParser) {
                try {
                    Patient patient = Patient.builder()
                        .id(UUID.fromString(validator
                            .isValidUUID(csvRecord.get("ID"),
                                csvRecord.getRecordNumber(),
                                "ID")))
                        .bDate(csvRecord.get("B_DATE"))
                        .refId(csvRecord.get("REF_ID"))
                        .accessDate(csvRecord.get("ACCESS_DATE"))
                        .items(Integer.parseInt(validator
                            .isValidInt(csvRecord.get("ITEMS"),
                                csvRecord.getRecordNumber(),
                                "ITEMS")))
                        .MPI(Integer.parseInt(validator
                            .isValidInt(csvRecord.get("MPI"),
                                csvRecord.getRecordNumber(),
                                "MPI")))
                        .patientTypeId(csvRecord.get("PATIENT_TYPE_ID"))
                        .patientTypeTxt(csvRecord.get("PATIENT_TYPE_TXT"))
                        .patientTypeRef(
                            UUID.fromString(validator
                                .isValidUUID(csvRecord
                                        .get("PATIENT_TYPE_REF"),
                                    csvRecord.getRecordNumber(),
                                    "PATIENT_TYPE_REF")))
                        .cDateTime(csvRecord.get("C_DATETIME"))
                        .uDateTime(csvRecord.get("U_DATETIME"))
                        .patientSrc(new Source(patientsPath
                            .getFileName().toString(),
                            csvRecord.getRecordNumber()))
                        .build();
                    patients.add(patient);
                } catch (RuntimeException e) {
                    continue;
                }
            }
        }
        return patients;
    }

    @Override
    public List<PatientContact> readPatientsContacts(List<Path> paths) throws IOException {
        try (
            java.io.Reader reader =
                Files.newBufferedReader(getContactsPath(paths));
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                .withAllowDuplicateHeaderNames()
                .withAllowMissingColumnNames()
                .withFirstRecordAsHeader()
                .withIgnoreHeaderCase());
        ) {
            for (CSVRecord csvRecord : csvParser) {
                System.out.println(csvRecord.toString());
            }
        }
        return null;
    }


    private Path getPatientPath(List<Path> paths) throws IOException {
        try (
            java.io.Reader reader = Files.newBufferedReader(paths.get(0));
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                .withAllowDuplicateHeaderNames()
                .withAllowMissingColumnNames()
                .withFirstRecordAsHeader()
                .withIgnoreHeaderCase())) {
            if (csvParser.getHeaderNames().size() < 20) {
                return paths.get(0);
            } else {
                return paths.get(1);
            }
        }
    }

    private Path getContactsPath(List<Path> paths) throws IOException {
        try (
            java.io.Reader reader = Files.newBufferedReader(paths.get(0));
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                .withAllowDuplicateHeaderNames()
                .withAllowMissingColumnNames()
                .withFirstRecordAsHeader()
                .withIgnoreHeaderCase())) {
            if (csvParser.getHeaderNames().size() > 20) {
                return paths.get(0);
            } else {
                return paths.get(1);
            }
        }
    }
}
