package com.softseve.migration.model;

import java.util.UUID;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Source {

    private int lineId;
    private String fileName;
    private LocalDateTime fileDateTime;
    private long lineNumber;
    private UUID clientId;
    private long clientId;

    public Source(String fileName, long lineNumber) {
        this.fileName = fileName;
        this.lineNumber = lineNumber;
    }
}
