package com.lettr.services.audience.contacts.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Request body for creating an audience contact.
 */
public class CreateAudienceContactOptions {

    private final String email;

    @SerializedName("list_id")
    private final String listId;

    private final Map<String, String> properties;

    @SerializedName("double_opt_in")
    private final DoubleOptInConfig doubleOptIn;

    private CreateAudienceContactOptions(Builder builder) {
        this.email = builder.email;
        this.listId = builder.listId;
        this.properties = builder.properties;
        this.doubleOptIn = builder.doubleOptIn;
    }

    @Nonnull
    public static Builder builder() {
        return new Builder();
    }

    @Nonnull public String getEmail() { return email; }
    @Nullable public String getListId() { return listId; }
    @Nullable public Map<String, String> getProperties() { return properties; }
    @Nullable public DoubleOptInConfig getDoubleOptIn() { return doubleOptIn; }

    public static class Builder {
        private String email;
        private String listId;
        private Map<String, String> properties;
        private DoubleOptInConfig doubleOptIn;

        private Builder() {}

        /** <b>(required)</b> The contact's email address. */
        @Nonnull
        public Builder email(@Nonnull String email) {
            this.email = email;
            return this;
        }

        /** <b>(optional)</b> Add the contact to this list on creation. */
        @Nonnull
        public Builder listId(@Nullable String listId) {
            this.listId = listId;
            return this;
        }

        /** <b>(optional)</b> Custom property values for this contact. */
        @Nonnull
        public Builder properties(@Nullable Map<String, String> properties) {
            this.properties = properties == null ? null : new LinkedHashMap<>(properties);
            return this;
        }

        /**
         * <b>(optional)</b> Send a double opt-in confirmation email; contact is
         * created in {@code unverified} status until they confirm.
         */
        @Nonnull
        public Builder doubleOptIn(@Nullable DoubleOptInConfig doubleOptIn) {
            this.doubleOptIn = doubleOptIn;
            return this;
        }

        @Nonnull
        public CreateAudienceContactOptions build() {
            if (email == null || email.isEmpty()) {
                throw new IllegalArgumentException("email is required");
            }
            return new CreateAudienceContactOptions(this);
        }
    }
}
