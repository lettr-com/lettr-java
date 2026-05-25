package com.lettr.services.audience.segments.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;

public class ListAudienceSegmentsParams {

    private final Integer page;
    private final Integer perPage;
    private final String listId;

    private ListAudienceSegmentsParams(Builder builder) {
        this.page = builder.page;
        this.perPage = builder.perPage;
        this.listId = builder.listId;
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
        if (listId != null) params.put("list_id", listId);
        return params;
    }

    public static class Builder {
        private Integer page;
        private Integer perPage;
        private String listId;

        private Builder() {}

        @Nonnull
        public Builder page(@Nullable Integer page) {
            this.page = page;
            return this;
        }

        @Nonnull
        public Builder perPage(@Nullable Integer perPage) {
            this.perPage = perPage;
            return this;
        }

        /** <b>(optional)</b> Restrict to segments scoped to this list. */
        @Nonnull
        public Builder listId(@Nullable String listId) {
            this.listId = listId;
            return this;
        }

        @Nonnull
        public ListAudienceSegmentsParams build() {
            return new ListAudienceSegmentsParams(this);
        }
    }
}
