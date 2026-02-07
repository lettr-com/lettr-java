package com.lettr.services.templates.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Response from listing templates.
 */
public class ListTemplatesResponse {

    private List<Template> templates;
    private Pagination pagination;

    public List<Template> getTemplates() {
        return templates;
    }

    public Pagination getPagination() {
        return pagination;
    }

    /**
     * Page-based pagination info.
     */
    public static class Pagination {
        private int total;

        @SerializedName("per_page")
        private int perPage;

        @SerializedName("current_page")
        private int currentPage;

        @SerializedName("last_page")
        private int lastPage;

        public int getTotal() { return total; }
        public int getPerPage() { return perPage; }
        public int getCurrentPage() { return currentPage; }
        public int getLastPage() { return lastPage; }
    }

    @Override
    public String toString() {
        return "ListTemplatesResponse{" +
                "templates=" + templates +
                ", pagination=" + pagination +
                '}';
    }
}
