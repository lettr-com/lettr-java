package com.lettr.services.campaigns.model;

import com.lettr.core.util.WireValues;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Parameters for listing campaign engagement events. All fields are optional.
 */
public class ListCampaignEventsParams {

    private final CampaignEventType eventType;
    private final String email;
    private final String startDate;
    private final String endDate;
    private final Integer limit;
    private final String cursor;

    private ListCampaignEventsParams(Builder builder) {
        this.eventType = builder.eventType;
        this.email = builder.email;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.limit = builder.limit;
        this.cursor = builder.cursor;
    }

    @Nonnull
    public static Builder builder() {
        return new Builder();
    }

    @Nonnull
    public Map<String, String> toQueryParams() {
        Map<String, String> params = new LinkedHashMap<>();
        if (eventType != null) params.put("event_type", WireValues.of(eventType));
        if (email != null) params.put("email", email);
        if (startDate != null) params.put("start_date", startDate);
        if (endDate != null) params.put("end_date", endDate);
        if (limit != null) params.put("limit", limit.toString());
        if (cursor != null) params.put("cursor", cursor);
        return params;
    }

    public static class Builder {
        private CampaignEventType eventType;
        private String email;
        private String startDate;
        private String endDate;
        private Integer limit;
        private String cursor;

        private Builder() {}

        /** <b>(optional)</b> Filters by event type. */
        @Nonnull
        public Builder eventType(@Nullable CampaignEventType eventType) {
            this.eventType = eventType;
            return this;
        }

        /** <b>(optional)</b> Filters by recipient email address. */
        @Nonnull
        public Builder email(@Nullable String email) {
            this.email = email;
            return this;
        }

        /** <b>(optional)</b> Only events at or after this time (ISO 8601). A date-only value is treated as the start of that day in UTC. */
        @Nonnull
        public Builder startDate(@Nullable String startDate) {
            this.startDate = startDate;
            return this;
        }

        /** <b>(optional)</b> Only events at or before this time, inclusive (ISO 8601). A date-only value covers the whole of that day in UTC. */
        @Nonnull
        public Builder endDate(@Nullable String endDate) {
            this.endDate = endDate;
            return this;
        }

        /** <b>(optional)</b> Max events per page (1–100, default 25). */
        @Nonnull
        public Builder limit(@Nullable Integer limit) {
            this.limit = limit;
            return this;
        }

        /** <b>(optional)</b> Pagination cursor from a previous response. */
        @Nonnull
        public Builder cursor(@Nullable String cursor) {
            this.cursor = cursor;
            return this;
        }

        @Nonnull
        public ListCampaignEventsParams build() {
            return new ListCampaignEventsParams(this);
        }
    }
}
