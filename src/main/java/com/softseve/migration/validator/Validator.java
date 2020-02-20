package com.softseve.migration.validator;

import java.util.UUID;

public class Validator {

    public String isValidUUID(String uuid, long line, String columnName) {

        try {
            UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid " + uuid + " " + columnName);
        }
        return uuid;
    }

    public String isValidLong(String number, long line, String columnName) {

        try {
            Long.parseLong(number);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid " + number + " " + columnName);
        }
        return number;
    }

}
