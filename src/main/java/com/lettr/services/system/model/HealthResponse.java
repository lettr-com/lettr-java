package com.lettr.services.system.model;

import javax.annotation.Nonnull;

/**
 * Response from the API health check endpoint.
 */
public class HealthResponse {

    private String status;
    private String timestamp;

    @Nonnull public String getStatus() { return status; }
    @Nonnull public String getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return "HealthResponse{status='" + status + "', timestamp='" + timestamp + "'}";
    }
}
