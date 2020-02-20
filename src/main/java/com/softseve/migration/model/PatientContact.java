package com.softseve.migration.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class PatientContact {

    private UUID id;
    private String cDate;
    private String contactRef;
    private String pCode;
    private String firstName;
    private String lastName;
    private String user;
    private int facility;
    private int cntRef;
    private int cntRef2;
    private String contactTypeId;
    private String contactType;
    private UUID contactTypeIdx;
    private int sums;
    private String cContactDateTime;
    private String uContactDateTime;
    private Source contactSrc;
}
