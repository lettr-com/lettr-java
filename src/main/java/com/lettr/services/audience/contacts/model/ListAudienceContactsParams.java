package com.lettr.services.audience.contacts.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Query parameters for listing audience contacts. All fields optional.
 */
public class ListAudienceContactsParams {

    private final Integer page;
    private final Integer perPage;
    private final String search;
    private final AudienceContactStatus status;
    private final String listId;
    private final String segmentId;

    private ListAudienceContactsParams(Builder builder) {
        this.page = builder.page;
        this.perPage = builder.perPage;
        this.search = builder.search;
        this.status = builder.status;
        this.listId = builder.listId;
        this.segmentId = builder.segmentId;
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
        if (search != null) params.put("search", search);
        if (status != null) params.put("status", status.name().toLowerCase(Locale.ROOT));
        if (listId != null) params.put("list_id", listId);
        if (segmentId != null) params.put("segment_id", segmentId);
        return params;
    }

    public static class Builder {
        private Integer page;
        private Integer perPage;
        private String search;
        private AudienceContactStatus status;
        private String listId;
        private String segmentId;

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

        /** <b>(optional)</b> Search by email or contact name (max 255). */
        @Nonnull
        public Builder search(@Nullable String search) {
            this.search = search;
            return this;
        }

        /** <b>(optional)</b> Filter by contact status. */
        @Nonnull
        public Builder status(@Nullable AudienceContactStatus status) {
            this.status = status;
            return this;
        }

        /** <b>(optional)</b> Restrict to contacts in this list. */
        @Nonnull
        public Builder listId(@Nullable String listId) {
            this.listId = listId;
            return this;
        }

        /** <b>(optional)</b> Restrict to contacts matching this segment. */
        @Nonnull
        public Builder segmentId(@Nullable String segmentId) {
            this.segmentId = segmentId;
            return this;
        }

        @Nonnull
        public ListAudienceContactsParams build() {
            return new ListAudienceContactsParams(this);
        }
    }
}
