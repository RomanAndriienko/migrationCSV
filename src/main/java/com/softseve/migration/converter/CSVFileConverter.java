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
        mapping.put("nameFromCSV1", "nameFromClass1");
        mapping.put("nameFromCSV2", "nameFromClass2");
        mapping.put("nameFromCSV3", "nameFromClass3");
        mapping.put("nameFromCSV4", "nameFromClass4");
        mapping.put("nameFromCSV5", "nameFromClass5");
        mapping.put("nameFromCSV6", "nameFromClass6");

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

//        CsvToBean csvToBean = new CsvToBean();
        csvToBean.setCsvReader(csvReader);
        csvToBean.setMappingStrategy(strategy);

        List<Patient> patients = csvToBean.parse();

        for (Patient patient : patients) {
            System.out.println(patient);
        }

        return patients;
    }
}
