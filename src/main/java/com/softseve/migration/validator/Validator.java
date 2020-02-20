package com.softseve.migration.validator;

import java.util.UUID;

public class Validator {

    public String isValidUUID(String uuid, long line, String columnName) {

        try {
            UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid " + columnName);
        }
        return uuid;
    }

    public String isValidInt(String number, long line, String columnName) {

        try {
            Integer.parseInt(number);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid " + columnName);
        }
        return number;
    }

}
