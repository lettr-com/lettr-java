package com.lettr.core.exception;

/**
 * Base exception for all Lettr SDK errors.
 */
public class LettrException extends Exception {

    public LettrException(String message) {
        super(message);
    }

    public LettrException(String message, Throwable cause) {
        super(message, cause);
    }
}
