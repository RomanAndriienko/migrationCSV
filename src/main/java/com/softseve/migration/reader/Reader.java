package com.softseve.migration.reader;

import java.nio.file.Path;
import java.util.List;

public interface Reader {

    List<String> read(List<Path> path);

}
