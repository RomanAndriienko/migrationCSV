package com.softseve.migration.loader;

import com.softseve.migration.model.PatientResult;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Service
public class Loader {

    private final int BATCH_SIZE = 50;
    private int customBatchSize = 0;

    // TODO: check this INSERT QUERY!!!
    private final String QUERY_LOAD_TO_DB = "insert into patient_result (ID, B_DATE, REF_ID, ACCESS_DATE, ITEMS, MPI, " +
            "PATIENT_TYPE_ID, PATIENT_TYPE_TXT, PATIENT_TYPE_REF, C_PATIENT_DATETIME, U_PATIENT_DATETIME, C_DATE, " +
            "CONTACT_REF, P_CODE, FIRST_NAME, LAST_NAME, USER_, FACILITY, CNT_REF, CNT_REF_2, CONTACT_TYPE_ID, " +
            "CONTACT_TYPE, CONTACT_TYPE_IDX, SUMS, C_CONTACT_DATE_TIME, U_CONTACT_DATE_TIME) " +
            "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; //26 elements

    private final JdbcTemplate jdbcTemplate;

    public Loader(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public Loader(JdbcTemplate jdbcTemplate, int customBatchSize) {
        this.jdbcTemplate = jdbcTemplate;
        if (customBatchSize > 0) this.customBatchSize = customBatchSize;
    }

    private int getCustomBatchSize() {
        return customBatchSize > 0 ? customBatchSize : BATCH_SIZE;
    }

    public void saveToDB(List<PatientResult> data) {
        jdbcTemplate.batchUpdate(QUERY_LOAD_TO_DB, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, String.valueOf(data.get(i).getId()));
                ps.setString(2, data.get(i).getBDate());
                ps.setString(3, data.get(i).getRefId());
                ps.setString(4, data.get(i).getAccessDate());
                ps.setLong(5, data.get(i).getItems());
                ps.setLong(6, data.get(i).getMPI());
                ps.setString(7, data.get(i).getPatientTypeId());
                ps.setString(8, data.get(i).getPatientTypeTxt());
                ps.setString(9, String.valueOf(data.get(i).getPatientTypeRef()));
                ps.setString(10, data.get(i).getCPatientDateTime());
                ps.setString(11, data.get(i).getUPatientDateTime());
                ps.setString(12, data.get(i).getCDate());
                ps.setString(13, data.get(i).getContactRef());
                ps.setString(14, data.get(i).getPCode());
                ps.setString(15, data.get(i).getFirstName());
                ps.setString(16, data.get(i).getLastName());
                ps.setString(17, data.get(i).getUser());
                ps.setLong(18, data.get(i).getFacility());
                ps.setLong(19, data.get(i).getCntRef());
                ps.setLong(20, data.get(i).getCntRef2());
                ps.setString(21, data.get(i).getContactTypeId());
                ps.setString(22, data.get(i).getContactType());
                ps.setString(23, String.valueOf(data.get(i).getContactTypeIdx()));
                ps.setLong(24, data.get(i).getSums());
                ps.setString(25, data.get(i).getCContactDateTime());
                ps.setString(26, data.get(i).getUContactDateTime());
            }

            @Override
            public int getBatchSize() {
                return getCustomBatchSize();
            }
        });
    }
}
