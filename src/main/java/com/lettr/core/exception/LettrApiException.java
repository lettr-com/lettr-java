package com.lettr.core.exception;

/**
 * Exception thrown when the Lettr API returns an error response.
 */
public class LettrApiException extends LettrException {

    private final int statusCode;
    private final String errorCode;

    public LettrApiException(String message, int statusCode, String errorCode) {
        super(message);
        this.statusCode = statusCode;
        this.errorCode = errorCode;
    }

    public LettrApiException(String message, int statusCode, String errorCode, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
        this.errorCode = errorCode;
    }

    /**
     * Returns the HTTP status code from the API response.
     *
     * @return HTTP status code
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Returns the error code from the API response body (e.g. "validation_error", "not_found").
     *
     * @return error code string, or null if not present
     */
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String toString() {
        return "LettrApiException{" +
                "message='" + getMessage() + '\'' +
                ", statusCode=" + statusCode +
                ", errorCode='" + errorCode + '\'' +
                '}';
    }
}
