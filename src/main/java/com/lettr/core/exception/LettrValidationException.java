package com.lettr.core.exception;

import java.util.List;
import java.util.Map;

/**
 * Exception thrown when the Lettr API returns a validation error (HTTP 422).
 */
public class LettrValidationException extends LettrApiException {

    private final Map<String, List<String>> errors;

    public LettrValidationException(String message, Map<String, List<String>> errors) {
        super(message, 422, "validation_error");
        this.errors = errors;
    }

    /**
     * Returns the field-level validation errors.
     * Keys are field names, values are lists of error messages for that field.
     *
     * @return map of field names to error messages
     */
    public Map<String, List<String>> getErrors() {
        return errors;
    }

    @Override
    public String toString() {
        return "LettrValidationException{" +
                "message='" + getMessage() + '\'' +
                ", errors=" + errors +
                '}';
    }
}
