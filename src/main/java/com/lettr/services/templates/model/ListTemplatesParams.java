package com.lettr.services.templates.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Parameters for listing templates with optional filtering and pagination.
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

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Converts this params object to a map of query parameters.
     */
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

        /**
         * Sets the project ID to list templates from.
         */
        public Builder projectId(int projectId) {
            this.projectId = projectId;
            return this;
        }

        /**
         * Sets the number of results per page (1-100).
         */
        public Builder perPage(int perPage) {
            this.perPage = perPage;
            return this;
        }

        /**
         * Sets the page number.
         */
        public Builder page(int page) {
            this.page = page;
            return this;
        }

        public ListTemplatesParams build() {
            return new ListTemplatesParams(this);
        }
    }
}
