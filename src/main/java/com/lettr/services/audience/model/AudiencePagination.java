package com.lettr.services.audience.model;

import com.google.gson.annotations.SerializedName;

/**
 * Pagination metadata returned alongside paginated audience list responses.
 */
public class AudiencePagination {

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
        return "AudiencePagination{total=" + total
                + ", perPage=" + perPage
                + ", currentPage=" + currentPage
                + ", lastPage=" + lastPage + '}';
    }
}
