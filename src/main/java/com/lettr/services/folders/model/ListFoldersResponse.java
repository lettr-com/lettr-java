package com.lettr.services.folders.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Response from listing folders.
 */
public class ListFoldersResponse {

    private List<Folder> folders;
    private Pagination pagination;

    @Nonnull public List<Folder> getFolders() { return folders; }
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
        return "ListFoldersResponse{folders=" + folders + '}';
    }
}
