package com.softseve.migration.watcher;

import com.softseve.migration.reader.Reader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import org.springframework.stereotype.Service;


@Service
public class Watcher {

    private Path directoryPath;
    private WatchService watchService;
    private WatchKey key;
    private final Reader reader;

    public Watcher(Reader reader) throws IOException {
        this.watchService = FileSystems.getDefault().newWatchService();
        this.reader = reader;
    }


    //better to use Enum fileType
    public void watch(String path, String fileType) throws InterruptedException,
        IOException {
        initDirectoryWatcher(path);
        while (true) {
            while ((key = watchService.take()) != null) {
                for (WatchEvent<?> event : key.pollEvents()) {
                    if (event.context().toString().endsWith(fileType)) {
                        Path pathToFile = Paths.get(directoryPath.getParent().toString(),
                            directoryPath.getFileName().toString(),
                            event.context().toString());
                        reader.read(pathToFile);
                    }
                }
                key.reset();
            }
        }
    }

    private void initDirectoryWatcher(String path) throws IOException {

        this.directoryPath = Paths.get(path);
        this.directoryPath.
            register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
    }
}
