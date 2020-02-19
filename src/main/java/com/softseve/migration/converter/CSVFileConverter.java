package com.softseve.migration.converter;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import com.softseve.migration.model.Patient;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CSVFileConverter implements Converter {

    private final CsvToBean<Patient> csvToBean;

    @Override
    public List<Patient> convert(Path path) {
        Map<String, String> mapping = new HashMap<>();
        mapping.put("ID", "id");
        mapping.put("B_DATE", "bDate");
        mapping.put("REF_ID", "refId");
        mapping.put("ACCESS_DATE", "accessDate");
        mapping.put("ITEMS", "items");
        mapping.put("MPI", "MPI");
        mapping.put("PATIENT_TYPE_ID", "patientTypeId");
        mapping.put("PATIENT_TYPE_TXT", "patientTypeTxt");
        mapping.put("PATIENT_TYPE_REF", "patientTypeRef");
        mapping.put("C_DATETIME", "cDateTime");
        mapping.put("U_DATETIME", "uDateTime");

        HeaderColumnNameTranslateMappingStrategy<Patient> strategy =
            new HeaderColumnNameTranslateMappingStrategy<>();

        strategy.setType(Patient.class);
        strategy.setColumnMapping(mapping);

        CSVReader csvReader = null;

        try {
            csvReader = new CSVReader(new FileReader(String.valueOf(path)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        csvToBean.setCsvReader(csvReader);
        csvToBean.setMappingStrategy(strategy);

        List<Patient> patients = csvToBean.parse();

        for (Patient patient : patients) {
            System.out.println(patient);
        }

        return patients;
    }
}
