package edu.ccrm.util;

public class DuplicateEnrollmentException extends RuntimeException {
    public DuplicateEnrollmentException(String message) {
        super(message);
    }
    
    public DuplicateEnrollmentException(String message, Throwable cause) {
        super(message, cause);
    }
}
