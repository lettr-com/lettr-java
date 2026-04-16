package com.lettr.services.projects.model;

import javax.annotation.Nonnull;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Parameters for listing projects. All fields are optional.
 */
public class ListProjectsParams {

    private final Integer perPage;
    private final Integer page;

    private ListProjectsParams(Builder builder) {
        this.perPage = builder.perPage;
        this.page = builder.page;
    }

    @Nonnull
    public static Builder builder() {
        return new Builder();
    }

    @Nonnull
    public Map<String, String> toQueryParams() {
        Map<String, String> params = new LinkedHashMap<>();
        if (perPage != null) params.put("per_page", perPage.toString());
        if (page != null) params.put("page", page.toString());
        return params;
    }

    public static class Builder {
        private Integer perPage;
        private Integer page;

        private Builder() {}

        /** <b>(optional)</b> Sets the number of results per page. */
        @Nonnull public Builder perPage(int perPage) { this.perPage = perPage; return this; }

        /** <b>(optional)</b> Sets the page number. */
        @Nonnull public Builder page(int page) { this.page = page; return this; }

        @Nonnull
        public ListProjectsParams build() {
            return new ListProjectsParams(this);
        }
    }
}
