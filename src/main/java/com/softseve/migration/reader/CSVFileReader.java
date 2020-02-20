package com.softseve.migration.reader;

import java.nio.file.Path;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CSVFileReader implements Reader {

    @Override
    public List<String> read(List<Path> paths) {
        return null;
    }
}
