package com.softseve.migration.converter;

import com.softseve.migration.model.Patient;
import java.nio.file.Path;
import java.util.List;

public interface Converter {

    List<Patient> convert(Path path);
}
