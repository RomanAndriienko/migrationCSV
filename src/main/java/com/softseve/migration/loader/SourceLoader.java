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
public class SourceLoader {

    private final int BATCH_SIZE = 10;
    private int customBatchSize = 0;

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
    public void loadContactInfo(List<PatientResult> data) {

        List<PatientResult> dataToLoad = data.stream()
                .filter(d -> Objects.nonNull(d.getContactSrc()))
                .collect(Collectors.toList());

        getDataParts(dataToLoad, getCustomBatchSize()).forEach(batch -> {
            jdbcTemplate.batchUpdate(QUERY_LOAD_TO_DB, new BatchPreparedStatementSetter() {

                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setString(1, batch.get(i).getContactSrc().getFileName());
                    ps.setString(2, LocalDateTime.now().toString());
                    ps.setLong(3, batch.get(i).getContactSrc().getLineNumber());
                    ps.setObject(4, batch.get(i).getId());
                }

                @Override
                public int getBatchSize() {
                    return batch.size();
                }
            });
        });
    }

    @Transactional(rollbackFor = Exception.class)
    public void loadPatientInfo(List<PatientResult> data) {

        List<PatientResult> dataToLoad = data.stream()
                .filter(d -> Objects.nonNull(d.getPatientSrc()))
                .collect(Collectors.toList());

        getDataParts(dataToLoad, getCustomBatchSize()).forEach(batch -> {
            jdbcTemplate.batchUpdate(QUERY_LOAD_TO_DB, new BatchPreparedStatementSetter() {

                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setString(1, batch.get(i).getPatientSrc().getFileName());
                    ps.setString(2, LocalDateTime.now().toString());
                    ps.setLong(3, batch.get(i).getPatientSrc().getLineNumber());
                    ps.setObject(4, batch.get(i).getId());
                }

                @Override
                public int getBatchSize() {
                    return batch.size();
                }
            });
        });
    }
}
