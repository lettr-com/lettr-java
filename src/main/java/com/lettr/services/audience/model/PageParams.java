package com.lettr.services.audience.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shared page/per_page parameters used by listing endpoints that only
 * support pagination (no other filters).
 */
public class PageParams {

    private final Integer page;
    private final Integer perPage;

    private PageParams(Builder builder) {
        this.page = builder.page;
        this.perPage = builder.perPage;
    }

    @Nonnull
    public static Builder builder() {
        return new Builder();
    }

    @Nonnull
    public Map<String, String> toQueryParams() {
        Map<String, String> params = new LinkedHashMap<>();
        if (page != null) params.put("page", page.toString());
        if (perPage != null) params.put("per_page", perPage.toString());
        return params;
    }

    public static class Builder {
        private Integer page;
        private Integer perPage;

        private Builder() {}

        /** <b>(optional)</b> Page number (min 1, default 1). */
        @Nonnull
        public Builder page(@Nullable Integer page) {
            this.page = page;
            return this;
        }

        /** <b>(optional)</b> Items per page (1–100, default 20). */
        @Nonnull
        public Builder perPage(@Nullable Integer perPage) {
            this.perPage = perPage;
            return this;
        }

        @Nonnull
        public PageParams build() {
            return new PageParams(this);
        }
    }
}
