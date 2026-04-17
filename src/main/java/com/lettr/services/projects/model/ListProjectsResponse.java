package com.lettr.services.projects.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Response from listing projects.
 */
public class ListProjectsResponse {

    private List<Project> projects;
    private Pagination pagination;

    @Nonnull public List<Project> getProjects() { return projects; }
    @Nonnull public Pagination getPagination() { return pagination; }

    /** Page-based pagination info. */
    public static class Pagination {
        private int total;

        @SerializedName("per_page")     private int perPage;
        @SerializedName("current_page") private int currentPage;
        @SerializedName("last_page")    private int lastPage;

        public int getTotal() { return total; }
        public int getPerPage() { return perPage; }
        public int getCurrentPage() { return currentPage; }
        public int getLastPage() { return lastPage; }
    }

    @Override
    public String toString() {
        return "ListProjectsResponse{projects=" + projects + '}';
    }
}
