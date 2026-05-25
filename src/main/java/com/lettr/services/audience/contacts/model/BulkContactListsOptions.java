package com.lettr.services.audience.contacts.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Shared request body for bulk attaching or detaching contacts and lists.
 * The endpoint applies the cartesian product of {@code contactIds} × {@code listIds}.
 */
public class BulkContactListsOptions {

    @SerializedName("contact_ids")
    private final List<String> contactIds;

    @SerializedName("list_ids")
    private final List<String> listIds;

    private BulkContactListsOptions(List<String> contactIds, List<String> listIds) {
        this.contactIds = contactIds;
        this.listIds = listIds;
    }

    @Nonnull
    public static BulkContactListsOptions of(@Nonnull List<String> contactIds, @Nonnull List<String> listIds) {
        if (contactIds == null || contactIds.isEmpty()) {
            throw new IllegalArgumentException("contactIds must contain at least one id");
        }
        if (contactIds.size() > 1000) {
            throw new IllegalArgumentException("contactIds cannot contain more than 1000 ids");
        }
        if (listIds == null || listIds.isEmpty()) {
            throw new IllegalArgumentException("listIds must contain at least one id");
        }
        if (listIds.size() > 50) {
            throw new IllegalArgumentException("listIds cannot contain more than 50 ids");
        }
        return new BulkContactListsOptions(new ArrayList<>(contactIds), new ArrayList<>(listIds));
    }

    @Nonnull public List<String> getContactIds() { return contactIds; }
    @Nonnull public List<String> getListIds() { return listIds; }
}
