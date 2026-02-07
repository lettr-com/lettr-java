package com.lettr.services.emails.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Response from listing sent emails.
 */
public class ListEmailsResponse {

    private List<EmailEvent> results;

    @SerializedName("total_count")
    private int totalCount;

    private Pagination pagination;

    public List<EmailEvent> getResults() {
        return results;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public Pagination getPagination() {
        return pagination;
    }

    /**
     * Cursor-based pagination info.
     */
    public static class Pagination {

        @SerializedName("next_cursor")
        private String nextCursor;

        @SerializedName("per_page")
        private int perPage;

        public String getNextCursor() {
            return nextCursor;
        }

        public int getPerPage() {
            return perPage;
        }
    }

    @Override
    public String toString() {
        return "ListEmailsResponse{" +
                "totalCount=" + totalCount +
                ", results=" + results +
                '}';
    }
}
