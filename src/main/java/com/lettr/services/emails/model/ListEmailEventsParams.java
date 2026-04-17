package com.lettr.services.emails.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Parameters for listing email events. All fields are optional.
 */
public class ListEmailEventsParams {

    private final List<String> events;
    private final List<String> recipients;
    private final String from;
    private final String to;
    private final Integer perPage;
    private final String cursor;
    private final String transmissions;
    private final String bounceClasses;

    private ListEmailEventsParams(Builder builder) {
        this.events = builder.events;
        this.recipients = builder.recipients;
        this.from = builder.from;
        this.to = builder.to;
        this.perPage = builder.perPage;
        this.cursor = builder.cursor;
        this.transmissions = builder.transmissions;
        this.bounceClasses = builder.bounceClasses;
    }

    @Nonnull
    public static Builder builder() {
        return new Builder();
    }

    @Nonnull
    public Map<String, String> toQueryParams() {
        Map<String, String> params = new LinkedHashMap<>();
        if (events != null && !events.isEmpty()) {
            params.put("events", String.join(",", events));
        }
        if (recipients != null && !recipients.isEmpty()) {
            params.put("recipients", String.join(",", recipients));
        }
        if (from != null) params.put("from", from);
        if (to != null) params.put("to", to);
        if (perPage != null) params.put("per_page", perPage.toString());
        if (cursor != null) params.put("cursor", cursor);
        if (transmissions != null) params.put("transmissions", transmissions);
        if (bounceClasses != null) params.put("bounce_classes", bounceClasses);
        return params;
    }

    public static class Builder {
        private List<String> events;
        private List<String> recipients;
        private String from;
        private String to;
        private Integer perPage;
        private String cursor;
        private String transmissions;
        private String bounceClasses;

        private Builder() {}

        /** <b>(optional)</b> Filters by event type (e.g. "delivery", "bounce", "open"). */
        @Nonnull
        public Builder events(@Nullable List<String> events) {
            this.events = events;
            return this;
        }

        /** <b>(optional)</b> Filters by recipient email addresses. */
        @Nonnull
        public Builder recipients(@Nullable List<String> recipients) {
            this.recipients = recipients;
            return this;
        }

        /** <b>(optional)</b> Filters events on or after this date (ISO 8601). */
        @Nonnull
        public Builder from(@Nullable String from) {
            this.from = from;
            return this;
        }

        /** <b>(optional)</b> Filters events on or before this date (ISO 8601). */
        @Nonnull
        public Builder to(@Nullable String to) {
            this.to = to;
            return this;
        }

        /** <b>(optional)</b> Sets the number of results per page. */
        @Nonnull
        public Builder perPage(int perPage) {
            this.perPage = perPage;
            return this;
        }

        /** <b>(optional)</b> Sets the pagination cursor from a previous response. */
        @Nonnull
        public Builder cursor(@Nullable String cursor) {
            this.cursor = cursor;
            return this;
        }

        /** <b>(optional)</b> Filters by transmission/request IDs (comma-separated). */
        @Nonnull
        public Builder transmissions(@Nullable String transmissions) {
            this.transmissions = transmissions;
            return this;
        }

        /** <b>(optional)</b> Filters by bounce class codes (comma-separated). */
        @Nonnull
        public Builder bounceClasses(@Nullable String bounceClasses) {
            this.bounceClasses = bounceClasses;
            return this;
        }

        @Nonnull
        public ListEmailEventsParams build() {
            return new ListEmailEventsParams(this);
        }
    }
}
