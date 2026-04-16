package com.lettr.services.projects;

import com.lettr.core.exception.LettrException;
import com.lettr.services.BaseService;
import com.lettr.services.projects.model.ListProjectsParams;
import com.lettr.services.projects.model.ListProjectsResponse;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Service for listing projects via the Lettr API.
 */
public class Projects extends BaseService {

    public Projects(@Nonnull String apiKey) {
        super(apiKey);
    }

    /**
     * List projects with optional pagination.
     *
     * @param params optional query parameters; pass null for defaults
     */
    @Nonnull
    public ListProjectsResponse list(@Nullable ListProjectsParams params) throws LettrException {
        return httpClient.get("/projects", params != null ? params.toQueryParams() : null, ListProjectsResponse.class);
    }

    /** List projects with default pagination. */
    @Nonnull
    public ListProjectsResponse list() throws LettrException {
        return list(null);
    }
}
