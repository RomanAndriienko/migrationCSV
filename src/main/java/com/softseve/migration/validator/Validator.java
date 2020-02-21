package com.softseve.migration.validator;

import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Validator {

    private static final Logger logger = LoggerFactory.getLogger(Validator.class);

    public String isValidUUID(String uuid, long line, String columnName) {

        try {
            UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            logger.error(String
                .format("Invalid value : %s in %s line in column %s", uuid, line, columnName));
            throw new RuntimeException("Invalid " + uuid + " " + columnName);
        }
        return uuid;
    }

    public String isValidLong(String number, long line, String columnName) {

        try {
            Long.parseLong(number);
        } catch (IllegalArgumentException e) {
            logger.error(String
                .format("Invalid value : %s in %s line in column %s", number, line, columnName));
            throw new RuntimeException("Invalid " + number + " " + columnName);
        }
        return number;
    }

}
