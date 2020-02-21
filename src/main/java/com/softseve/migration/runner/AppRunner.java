package com.softseve.migration.runner;

import com.softseve.migration.converter.Converter;
import com.softseve.migration.loader.Loader;
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

//     specify directory for watcher with csv files
//     add files in runtime

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Watcher watcher = new Watcher(
                new Processor(new Loader(jdbcTemplate), new CSVFileReader(), new Converter()));
        System.out.println("waiting...");
        watcher.watch(pathToCsv, FileType.CSV);
    }
}
