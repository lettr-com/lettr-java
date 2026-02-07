package com.lettr.services.emails.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Parameters for listing sent emails with optional filtering and pagination.
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * ListEmailsParams params = ListEmailsParams.builder()
 *     .perPage(50)
 *     .recipients("user@example.com")
 *     .build();
 * }</pre>
 */
public class ListEmailsParams {

    private final Integer perPage;
    private final String cursor;
    private final String recipients;
    private final String from;
    private final String to;

    private ListEmailsParams(Builder builder) {
        this.perPage = builder.perPage;
        this.cursor = builder.cursor;
        this.recipients = builder.recipients;
        this.from = builder.from;
        this.to = builder.to;
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Converts this params object to a map of query parameters.
     */
    public Map<String, String> toQueryParams() {
        Map<String, String> params = new HashMap<>();
        if (perPage != null) params.put("per_page", perPage.toString());
        if (cursor != null) params.put("cursor", cursor);
        if (recipients != null) params.put("recipients", recipients);
        if (from != null) params.put("from", from);
        if (to != null) params.put("to", to);
        return params;
    }

    public static class Builder {
        private Integer perPage;
        private String cursor;
        private String recipients;
        private String from;
        private String to;

        private Builder() {}

        /**
         * Sets the number of results per page (1-100).
         */
        public Builder perPage(int perPage) {
            this.perPage = perPage;
            return this;
        }

        /**
         * Sets the pagination cursor from a previous response.
         */
        public Builder cursor(String cursor) {
            this.cursor = cursor;
            return this;
        }

        /**
         * Filters by recipient email address.
         */
        public Builder recipients(String recipients) {
            this.recipients = recipients;
            return this;
        }

        /**
         * Filters emails sent on or after this date (ISO 8601 format, e.g. "2024-01-01").
         */
        public Builder from(String from) {
            this.from = from;
            return this;
        }

        /**
         * Filters emails sent on or before this date (ISO 8601 format, e.g. "2024-12-31").
         */
        public Builder to(String to) {
            this.to = to;
            return this;
        }

        public ListEmailsParams build() {
            return new ListEmailsParams(this);
        }
    }
}
