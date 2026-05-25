package com.lettr.services.audience.contacts.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Request body for bulk-creating up to 1000 audience contacts.
 */
public class BulkCreateAudienceContactsOptions {

    private final List<String> emails;

    @SerializedName("list_id")
    private final String listId;

    private final Map<String, String> properties;

    private BulkCreateAudienceContactsOptions(Builder builder) {
        this.emails = builder.emails;
        this.listId = builder.listId;
        this.properties = builder.properties;
    }

    @Nonnull
    public static Builder builder() {
        return new Builder();
    }

    @Nonnull public List<String> getEmails() { return emails; }
    @Nullable public String getListId() { return listId; }
    @Nullable public Map<String, String> getProperties() { return properties; }

    public static class Builder {
        private List<String> emails;
        private String listId;
        private Map<String, String> properties;

        private Builder() {}

        /** <b>(required)</b> 1–1000 email addresses. */
        @Nonnull
        public Builder emails(@Nonnull List<String> emails) {
            this.emails = emails == null ? null : new ArrayList<>(emails);
            return this;
        }

        /** <b>(optional)</b> Add all created contacts to this list. */
        @Nonnull
        public Builder listId(@Nullable String listId) {
            this.listId = listId;
            return this;
        }

        /** <b>(optional)</b> Custom property values applied to every created contact. */
        @Nonnull
        public Builder properties(@Nullable Map<String, String> properties) {
            this.properties = properties == null ? null : new LinkedHashMap<>(properties);
            return this;
        }

        @Nonnull
        public BulkCreateAudienceContactsOptions build() {
            if (emails == null || emails.isEmpty()) {
                throw new IllegalArgumentException("emails must contain at least one address");
            }
            if (emails.size() > 1000) {
                throw new IllegalArgumentException("emails cannot contain more than 1000 addresses");
            }
            return new BulkCreateAudienceContactsOptions(this);
        }
    }
}
