package com.lettr.services.emails.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Response from listing email events.
 */
public class ListEmailEventsResponse {

    private Events events;

    @Nonnull public Events getEvents() { return events; }

    /** Container for email events with pagination. */
    public static class Events {

        private List<EmailEvent> data;

        @SerializedName("total_count")
        private int totalCount;

        private String from;
        private String to;
        private Pagination pagination;

        @Nonnull public List<EmailEvent> getData() { return data; }
        public int getTotalCount() { return totalCount; }
        @Nonnull public String getFrom() { return from; }
        @Nonnull public String getTo() { return to; }
        @Nonnull public Pagination getPagination() { return pagination; }
    }

    /** Cursor-based pagination info. */
    public static class Pagination {

        @SerializedName("next_cursor")
        private String nextCursor;

        @SerializedName("per_page")
        private int perPage;

        /** Cursor for the next page, or null if there are no more results. */
        @Nullable public String getNextCursor() { return nextCursor; }
        public int getPerPage() { return perPage; }
    }

    @Override
    public String toString() {
        return "ListEmailEventsResponse{events=" + (events != null ? "totalCount=" + events.totalCount : "null") + '}';
    }
}
