package com.wcc.hospital.exception;

public class DuplicateNationalIdException extends RuntimeException {
    public DuplicateNationalIdException(String nationalId) {
        super("A patient with National ID " + nationalId + " is already registered");
    }
}
