package com.lettr.services.system;

import com.lettr.core.exception.LettrException;
import com.lettr.services.BaseService;
import com.lettr.services.system.model.AuthCheckResponse;
import com.lettr.services.system.model.HealthResponse;

import javax.annotation.Nonnull;

/**
 * Service for system-level operations (health check, API key validation).
 */
public class System extends BaseService {

    public System(@Nonnull String apiKey) {
        super(apiKey);
    }

    /**
     * Check the health status of the API. Does not require authentication.
     *
     * @return health status with timestamp
     * @throws LettrException if the request fails
     */
    @Nonnull
    public HealthResponse health() throws LettrException {
        return httpClient.get("/health", null, HealthResponse.class);
    }

    /**
     * Validate the configured API key and return associated team information.
     *
     * @return team ID and timestamp
     * @throws LettrException if the request fails (e.g. 401 for invalid key)
     */
    @Nonnull
    public AuthCheckResponse authCheck() throws LettrException {
        return httpClient.get("/auth/check", null, AuthCheckResponse.class);
    }
}
