package com.softseve.migration.model;

import lombok.Data;

import java.util.UUID;

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
    private String cDateTime;
    private String uDateTime;
}
