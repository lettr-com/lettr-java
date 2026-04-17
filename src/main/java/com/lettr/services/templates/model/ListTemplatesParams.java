package com.lettr.services.templates.model;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * Parameters for listing templates with optional filtering and pagination.
 * All fields are optional.
 */
public class ListTemplatesParams {

    private final Integer projectId;
    private final Integer perPage;
    private final Integer page;

    private ListTemplatesParams(Builder builder) {
        this.projectId = builder.projectId;
        this.perPage = builder.perPage;
        this.page = builder.page;
    }

    @Nonnull
    public static Builder builder() {
        return new Builder();
    }

    @Nonnull
    public Map<String, String> toQueryParams() {
        Map<String, String> params = new HashMap<>();
        if (projectId != null) params.put("project_id", projectId.toString());
        if (perPage != null) params.put("per_page", perPage.toString());
        if (page != null) params.put("page", page.toString());
        return params;
    }

    public static class Builder {
        private Integer projectId;
        private Integer perPage;
        private Integer page;

        private Builder() {}

        /** <b>(optional)</b> Filters templates by project ID. */
        @Nonnull public Builder projectId(int projectId) { this.projectId = projectId; return this; }

        /** <b>(optional)</b> Sets the number of results per page (1–100). */
        @Nonnull public Builder perPage(int perPage) { this.perPage = perPage; return this; }

        /** <b>(optional)</b> Sets the page number. */
        @Nonnull public Builder page(int page) { this.page = page; return this; }

        @Nonnull
        public ListTemplatesParams build() {
            return new ListTemplatesParams(this);
        }
    }
}
