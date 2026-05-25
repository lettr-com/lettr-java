package com.lettr.services.audience.contacts.model;

import com.google.gson.annotations.JsonAdapter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Request body for partially updating an audience contact. All fields optional.
 *
 * <p>For {@code properties}, setting a value to {@code null} removes that property
 * from the contact.</p>
 */
public class UpdateAudienceContactOptions {

    private final String email;
    private final AudienceContactStatus status;

    @JsonAdapter(NullablePropertiesAdapter.class)
    private final Map<String, String> properties;

    private UpdateAudienceContactOptions(Builder builder) {
        this.email = builder.email;
        this.status = builder.status;
        this.properties = builder.properties;
    }

    @Nonnull
    public static Builder builder() {
        return new Builder();
    }

    @Nullable public String getEmail() { return email; }
    @Nullable public AudienceContactStatus getStatus() { return status; }
    @Nullable public Map<String, String> getProperties() { return properties; }

    public static class Builder {
        private String email;
        private AudienceContactStatus status;
        private Map<String, String> properties;

        private Builder() {}

        /** <b>(optional)</b> New email address (must remain unique). */
        @Nonnull
        public Builder email(@Nullable String email) {
            this.email = email;
            return this;
        }

        /**
         * <b>(optional)</b> New status. The API only accepts {@link AudienceContactStatus#SUBSCRIBED}
         * or {@link AudienceContactStatus#UNSUBSCRIBED} when updating a contact; other statuses
         * (bounced, complained, unverified) are server-managed.
         *
         * @throws IllegalArgumentException if {@code status} is not {@code SUBSCRIBED} or {@code UNSUBSCRIBED}
         */
        @Nonnull
        public Builder status(@Nullable AudienceContactStatus status) {
            if (status != null
                    && status != AudienceContactStatus.SUBSCRIBED
                    && status != AudienceContactStatus.UNSUBSCRIBED) {
                throw new IllegalArgumentException(
                        "status must be SUBSCRIBED or UNSUBSCRIBED when updating a contact");
            }
            this.status = status;
            return this;
        }

        /**
         * <b>(optional)</b> Partial property update. Use {@code null} as a value
         * to remove the property.
         */
        @Nonnull
        public Builder properties(@Nullable Map<String, String> properties) {
            this.properties = properties == null ? null : new LinkedHashMap<>(properties);
            return this;
        }

        @Nonnull
        public UpdateAudienceContactOptions build() {
            return new UpdateAudienceContactOptions(this);
        }
    }
}
