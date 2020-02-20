package com.softseve.migration.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Source {

    private int lineId;
    private String fileName;
    private LocalDateTime fileDateTime;
    private long lineNumber;
    private long clientId;

    public Source(String fileName, long lineNumber) {
        this.fileName = fileName;
        this.lineNumber = lineNumber;
    }
}
