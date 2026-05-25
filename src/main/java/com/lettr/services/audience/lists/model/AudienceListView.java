package com.lettr.services.audience.lists.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;

/**
 * Represents a single audience list as returned by the API.
 */
public class AudienceListView {

    private String id;
    private String name;

    @SerializedName("contacts_count")
    private int contactsCount;

    @Nonnull
    public String getId() {
        return id;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    public int getContactsCount() {
        return contactsCount;
    }

    @Override
    public String toString() {
        return "AudienceListView{id='" + id + "', name='" + name + "', contactsCount=" + contactsCount + '}';
    }
}
