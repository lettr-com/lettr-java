package com.lettr.core.model;

import com.google.gson.annotations.SerializedName;

/**
 * Offset-based pagination metadata returned alongside paginated list responses
 * across the SDK (audience, campaigns, …).
 */
public class OffsetPagination {

    private int total;

    @SerializedName("per_page")
    private int perPage;

    @SerializedName("current_page")
    private int currentPage;

    @SerializedName("last_page")
    private int lastPage;

    public int getTotal() {
        return total;
    }

    public int getPerPage() {
        return perPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    @Override
    public String toString() {
        return "OffsetPagination{total=" + total
                + ", perPage=" + perPage
                + ", currentPage=" + currentPage
                + ", lastPage=" + lastPage + '}';
    }
}
