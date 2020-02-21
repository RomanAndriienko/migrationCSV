package com.softseve.migration.runner;

import com.softseve.migration.converter.Converter;
import com.softseve.migration.loader.Loader;
import com.softseve.migration.processor.Processor;
import com.softseve.migration.reader.CSVFileReader;
import com.softseve.migration.watcher.FileType;
import com.softseve.migration.watcher.Watcher;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements ApplicationRunner {

//     specify directory for watcher with csv files
//     add files in runtime

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Watcher watcher = new Watcher(
            new Processor(new Loader(), new CSVFileReader(), new Converter()));
        watcher.watch("C:\\Users\\randr\\data", FileType.CSV);
    }
}
