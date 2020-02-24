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
import java.util.List;

@Service
public class SourceLoader {


    private final int BATCH_SIZE = 50;
    private int customBatchSize = 0;

    // TODO: check this INSERT QUERY!!!
    private final String QUERY_LOAD_TO_DB = "insert into source (FILE_NAME, DATE_TIME, LINE_NUMBER, CLIENT_ID) " +
            "values (?,?,?,?)";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SourceLoader(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public SourceLoader(JdbcTemplate jdbcTemplate, int customBatchSize) {
        this.jdbcTemplate = jdbcTemplate;
        if (customBatchSize > 0) this.customBatchSize = customBatchSize;
    }

    private int getCustomBatchSize() {
        return customBatchSize > 0 ? customBatchSize : BATCH_SIZE;
    }

    @Transactional(rollbackFor = Exception.class)
    public void loadContactInfo(List<PatientResult> data) {
        jdbcTemplate.batchUpdate(QUERY_LOAD_TO_DB, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, data.get(i).getContactSrc().getFileName());
                ps.setString(2, LocalDateTime.now().toString());
                ps.setLong(3, data.get(i).getContactSrc().getLineNumber());
                ps.setString(4, data.get(i).getId().toString());
            }

            @Override
            public int getBatchSize() {
                return getCustomBatchSize();
            }
        });
    }

    @Transactional(rollbackFor = Exception.class)
    public void loadPatientInfo(List<PatientResult> data) {
        jdbcTemplate.batchUpdate(QUERY_LOAD_TO_DB, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, data.get(i).getPatientSrc().getFileName());
                ps.setString(2, LocalDateTime.now().toString());
                ps.setLong(3, data.get(i).getPatientSrc().getLineNumber());
                ps.setString(4, data.get(i).getId().toString());
            }

            @Override
            public int getBatchSize() {
                return getCustomBatchSize();
            }
        });
    }
}
