package com.softseve.migration.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Patient {

    private UUID id;
    private String bDate;
    private String refId;
    private String accessDate;
    private int items;
    private int MPI;
    private String patientTypeId;
    private String patientTypeTxt;
    private UUID patientTypeRef;
    private String cDateTime;
    private String uDateTime;
    private Source patientSrc;
}
