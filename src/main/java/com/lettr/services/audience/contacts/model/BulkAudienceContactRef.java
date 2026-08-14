package com.lettr.services.audience.contacts.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Identity of a contact that exists after a bulk create, so the caller can
 * chain into the bulk list and topic endpoints without looking ids up again.
 */
public class BulkAudienceContactRef {

    private String id;

    private String email;

    private boolean created;

    @Nullable public String getId() { return id; }

    @Nullable public String getEmail() { return email; }

    /** {@code true} when this request created the contact, {@code false} when it already existed. */
    public boolean isCreated() { return created; }

    @Override
    @Nonnull
    public String toString() {
        return "BulkAudienceContactRef{id='" + id + "', email='" + email + "', created=" + created + '}';
    }
}
