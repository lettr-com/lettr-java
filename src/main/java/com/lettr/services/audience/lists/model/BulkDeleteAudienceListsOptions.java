package com.lettr.services.audience.lists.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Request body for bulk-deleting audience lists. Accepts 1–50 list IDs.
 */
public class BulkDeleteAudienceListsOptions {

    @SerializedName("list_ids")
    private final List<String> listIds;

    private BulkDeleteAudienceListsOptions(List<String> listIds) {
        this.listIds = listIds;
    }

    @Nonnull
    public static BulkDeleteAudienceListsOptions of(@Nonnull List<String> listIds) {
        if (listIds == null || listIds.isEmpty()) {
            throw new IllegalArgumentException("listIds must contain at least one id");
        }
        if (listIds.size() > 50) {
            throw new IllegalArgumentException("listIds cannot contain more than 50 ids");
        }
        return new BulkDeleteAudienceListsOptions(new ArrayList<>(listIds));
    }

    @Nonnull
    public List<String> getListIds() {
        return listIds;
    }
}
