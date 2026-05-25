package com.lettr.services.audience.lists.model;

/**
 * Response for bulk delete of audience lists.
 */
public class BulkDeleteAudienceListsResponse {

    private int deleted;

    public int getDeleted() {
        return deleted;
    }

    @Override
    public String toString() {
        return "BulkDeleteAudienceListsResponse{deleted=" + deleted + '}';
    }
}
