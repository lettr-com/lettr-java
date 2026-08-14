package com.lettr.services.audience.contacts.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * One contact in a bulk-create payload.
 *
 * <p>{@code listIds} and {@code topics} here are applied <b>on top of</b> the
 * batch-wide ones on {@link BulkCreateAudienceContactsOptions}; a
 * {@code properties} key here overrides the batch-wide value for the same key,
 * and a row-level opt-out beats a batch-level opt-in.
 *
 * <p>A row that fails validation is skipped rather than failing the request —
 * it comes back in {@link BulkCreateAudienceContactsResponse#getErrors()}.
 */
public class BulkAudienceContactRow {

    private final String email;

    private final Map<String, String> properties;

    @SerializedName("list_ids")
    private final List<String> listIds;

    private final List<AudienceTopicSubscription> topics;

    private BulkAudienceContactRow(Builder builder) {
        this.email = builder.email;
        this.properties = builder.properties;
        this.listIds = builder.listIds;
        this.topics = builder.topics;
    }

    /** A row with nothing but an address — it inherits everything batch-wide. */
    @Nonnull
    public static BulkAudienceContactRow of(@Nonnull String email) {
        return builder().email(email).build();
    }

    @Nonnull
    public static Builder builder() {
        return new Builder();
    }

    @Nonnull public String getEmail() { return email; }
    @Nullable public Map<String, String> getProperties() { return properties; }
    @Nullable public List<String> getListIds() { return listIds; }
    @Nullable public List<AudienceTopicSubscription> getTopics() { return topics; }

    public static class Builder {
        private String email;
        private Map<String, String> properties;
        private List<String> listIds;
        private List<AudienceTopicSubscription> topics;

        private Builder() {}

        /** <b>(required)</b> The contact's email address. */
        @Nonnull
        public Builder email(@Nonnull String email) {
            this.email = email;
            return this;
        }

        /**
         * <b>(optional)</b> Property values for this contact. Each key must match
         * a property defined for the team, and wins over the batch-wide value.
         */
        @Nonnull
        public Builder properties(@Nullable Map<String, String> properties) {
            this.properties = properties == null ? null : new LinkedHashMap<>(properties);
            return this;
        }

        /** <b>(optional)</b> Up to 50 lists for this row, on top of the batch-wide ones. */
        @Nonnull
        public Builder listIds(@Nullable List<String> listIds) {
            this.listIds = listIds == null ? null : new ArrayList<>(listIds);
            return this;
        }

        /** <b>(optional)</b> Up to 50 topic subscriptions for this row. */
        @Nonnull
        public Builder topics(@Nullable List<AudienceTopicSubscription> topics) {
            this.topics = topics == null ? null : new ArrayList<>(topics);
            return this;
        }

        /** <b>(optional)</b> Convenience for a single topic subscription. */
        @Nonnull
        public Builder topic(@Nonnull AudienceTopicSubscription topic) {
            return topics(Collections.singletonList(topic));
        }

        @Nonnull
        public BulkAudienceContactRow build() {
            if (email == null || email.isEmpty()) {
                throw new IllegalArgumentException("email is required");
            }
            if (listIds != null && listIds.size() > 50) {
                throw new IllegalArgumentException("listIds cannot contain more than 50 ids");
            }
            if (topics != null && topics.size() > 50) {
                throw new IllegalArgumentException("topics cannot contain more than 50 subscriptions");
            }
            return new BulkAudienceContactRow(this);
        }
    }
}
