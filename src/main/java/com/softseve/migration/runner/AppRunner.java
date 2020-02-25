package com.softseve.migration.runner;

import com.softseve.migration.converter.Converter;
import com.softseve.migration.loader.PatientLoader;
import com.softseve.migration.loader.SourceLoader;
import com.softseve.migration.processor.Processor;
import com.softseve.migration.reader.CSVFileReader;
import com.softseve.migration.watcher.FileType;
import com.softseve.migration.watcher.Watcher;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppRunner implements ApplicationRunner {

    @Value("${pathToCsv}")
    public String pathToCsv;

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Watcher watcher = new Watcher(
            new Processor(new PatientLoader(jdbcTemplate), new SourceLoader(jdbcTemplate),
                new CSVFileReader(), new Converter()));
        System.out.println("waiting...");
        watcher.watch(pathToCsv, FileType.CSV);
    }
}
