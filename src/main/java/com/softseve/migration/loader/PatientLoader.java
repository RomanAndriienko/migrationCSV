package com.softseve.migration.loader;

import com.softseve.migration.model.PatientResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PatientLoader {

    private final int BATCH_SIZE = 50;
    private int customBatchSize = 0;

    // TODO: check this INSERT QUERY!!!
    private final String QUERY_LOAD_TO_DB = "insert into patient_result (ID, B_DATE, REF_ID, ACCESS_DATE, ITEMS, MPI, " +
            "PATIENT_TYPE_ID, PATIENT_TYPE_TXT, PATIENT_TYPE_REF, C_PATIENT_DATETIME, U_PATIENT_DATETIME, C_DATE, " +
            "CONTACT_REF, P_CODE, FIRST_NAME, LAST_NAME, USER_, FACILITY, CNT_REF, CNT_REF_2, CONTACT_TYPE_ID, " +
            "CONTACT_TYPE, CONTACT_TYPE_IDX, SUMS, C_CONTACT_DATE_TIME, U_CONTACT_DATE_TIME) " +
            "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; //26 elements

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PatientLoader(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public PatientLoader(JdbcTemplate jdbcTemplate, int customBatchSize) {
        this.jdbcTemplate = jdbcTemplate;
        if (customBatchSize > 0) this.customBatchSize = customBatchSize;
    }

    private int getCustomBatchSize() {
        return customBatchSize > 0 ? customBatchSize : BATCH_SIZE;
    }

    private int getOperationNumber(List<PatientResult> data) {
        int operationNum;
        if (data.size() % getCustomBatchSize() == 0) {
            operationNum = data.size() / getCustomBatchSize();
        } else {
            operationNum = data.size() / getCustomBatchSize() + 1;
        }
        return operationNum;
    }

    private List<List<PatientResult>> getDataParts(List<PatientResult> data, int batchSize) {
        List<List<PatientResult>> patientResults = new ArrayList<>();

        for (int i = 0; i < getOperationNumber(data); i++) {
            int start = i * batchSize;
            int end = start + batchSize;
            if (end > data.size()) end = data.size();

            patientResults.add(data.subList(start, end));
        }
        return patientResults;
    }

    @Transactional(rollbackFor = Exception.class)
    public void load(List<PatientResult> data) {

        List<PatientResult> dataToLoad = data.stream()
                .filter(d -> Objects.nonNull(d.getContactSrc()))
                .collect(Collectors.toList());

        getDataParts(dataToLoad, getCustomBatchSize()).forEach(batch -> {
            jdbcTemplate.batchUpdate(QUERY_LOAD_TO_DB, new BatchPreparedStatementSetter() {

                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setObject(1, batch.get(i).getId());
                    ps.setString(2, batch.get(i).getBDate());
                    ps.setString(3, batch.get(i).getRefId());
                    ps.setString(4, batch.get(i).getAccessDate());
                    ps.setLong(5, batch.get(i).getItems());
                    ps.setLong(6, batch.get(i).getMPI());
                    ps.setString(7, batch.get(i).getPatientTypeId());
                    ps.setString(8, batch.get(i).getPatientTypeTxt());
                    ps.setString(9, batch.get(i).getPatientTypeRef());
                    ps.setString(10, batch.get(i).getCPatientDateTime());
                    ps.setString(11, batch.get(i).getUPatientDateTime());
                    ps.setString(12, batch.get(i).getCDate());
                    ps.setString(13, batch.get(i).getContactRef());
                    ps.setString(14, batch.get(i).getPCode());
                    ps.setString(15, batch.get(i).getFirstName());
                    ps.setString(16, batch.get(i).getLastName());
                    ps.setString(17, batch.get(i).getUser());
                    ps.setLong(18, batch.get(i).getFacility());
                    ps.setLong(19, batch.get(i).getCntRef());
                    ps.setLong(20, batch.get(i).getCntRef2());
                    ps.setString(21, batch.get(i).getContactTypeId());
                    ps.setString(22, batch.get(i).getContactType());
                    ps.setObject(23, batch.get(i).getContactTypeIdx());
                    ps.setLong(24, batch.get(i).getSums());
                    ps.setString(25, batch.get(i).getCContactDateTime());
                    ps.setString(26, batch.get(i).getUContactDateTime());
                }

                @Override
                public int getBatchSize() {
                    return batch.size();
                }
            });
        });
    }
}
