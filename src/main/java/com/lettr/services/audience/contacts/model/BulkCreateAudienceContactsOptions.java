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
 *
 * <p>Two shapes are supported, and exactly one of them must be filled in:
 *
 * <ul>
 *   <li>{@code emails} — a flat list of addresses that all share the batch-wide
 *       {@code listId}/{@code listIds}, {@code properties} and {@code topics}.
 *       This is the original shape and is unchanged.</li>
 *   <li>{@code contacts} — one {@link BulkAudienceContactRow} per contact, each
 *       with its own properties, lists and topic subscriptions.</li>
 * </ul>
 *
 * <p>Batch-wide {@code listIds} and {@code topics} are unioned into every row; a
 * row-level property key or opt-out wins over the batch-wide value.
 *
 * <pre>{@code
 * BulkCreateAudienceContactsOptions.builder()
 *     .contacts(List.of(
 *         BulkAudienceContactRow.builder()
 *             .email("cara@example.com")
 *             .properties(Map.of("plan", "pro"))
 *             .build(),
 *         BulkAudienceContactRow.builder()
 *             .email("dan@example.com")
 *             .topic(AudienceTopicSubscription.optOut("01h-promos"))
 *             .build()))
 *     .listIds(List.of("01h-everyone"))
 *     .updateExisting(true)
 *     .build();
 * }</pre>
 *
 * @see BulkCreateAudienceContactsResponse for how partial failures are reported.
 */
public class BulkCreateAudienceContactsOptions {

    private final List<String> emails;

    @SerializedName("list_id")
    private final String listId;

    private final Map<String, String> properties;

    private final List<BulkAudienceContactRow> contacts;

    @SerializedName("list_ids")
    private final List<String> listIds;

    private final List<AudienceTopicSubscription> topics;

    // Boxed so it can stay null and be omitted from the payload when false —
    // a legacy request then serializes byte-identically. The API defaults it
    // to false anyway.
    @SerializedName("update_existing")
    private final Boolean updateExisting;

    private BulkCreateAudienceContactsOptions(Builder builder) {
        this.emails = builder.emails;
        this.listId = builder.listId;
        this.properties = builder.properties;
        this.contacts = builder.contacts;
        this.listIds = builder.listIds;
        this.topics = builder.topics;
        this.updateExisting = builder.updateExisting ? Boolean.TRUE : null;
    }

    @Nonnull
    public static Builder builder() {
        return new Builder();
    }

    @Nullable public List<String> getEmails() { return emails; }
    @Nullable public String getListId() { return listId; }
    @Nullable public Map<String, String> getProperties() { return properties; }
    @Nullable public List<BulkAudienceContactRow> getContacts() { return contacts; }
    @Nullable public List<String> getListIds() { return listIds; }
    @Nullable public List<AudienceTopicSubscription> getTopics() { return topics; }
    public boolean isUpdateExisting() { return Boolean.TRUE.equals(updateExisting); }

    public static class Builder {
        private List<String> emails;
        private String listId;
        private Map<String, String> properties;
        private List<BulkAudienceContactRow> contacts;
        private List<String> listIds;
        private List<AudienceTopicSubscription> topics;
        private boolean updateExisting;

        private Builder() {}

        /**
         * 1–1000 email addresses that all share the batch-wide settings.
         * Required unless {@link #contacts(List)} is used.
         */
        @Nonnull
        public Builder emails(@Nullable List<String> emails) {
            this.emails = emails == null ? null : new ArrayList<>(emails);
            return this;
        }

        /** <b>(optional)</b> Add all contacts to this list. Folded into {@code listIds} server-side. */
        @Nonnull
        public Builder listId(@Nullable String listId) {
            this.listId = listId;
            return this;
        }

        /**
         * <b>(optional)</b> Property values applied to every contact in the
         * batch. A row's own key wins over these.
         */
        @Nonnull
        public Builder properties(@Nullable Map<String, String> properties) {
            this.properties = properties == null ? null : new LinkedHashMap<>(properties);
            return this;
        }

        /**
         * 1–1000 rows, each with its own properties, lists and topic
         * subscriptions. Required unless {@link #emails(List)} is used.
         */
        @Nonnull
        public Builder contacts(@Nullable List<BulkAudienceContactRow> contacts) {
            this.contacts = contacts == null ? null : new ArrayList<>(contacts);
            return this;
        }

        /**
         * <b>(optional)</b> Up to 50 batch-wide lists, unioned into every row on
         * top of the row's own {@code listIds}.
         */
        @Nonnull
        public Builder listIds(@Nullable List<String> listIds) {
            this.listIds = listIds == null ? null : new ArrayList<>(listIds);
            return this;
        }

        /** <b>(optional)</b> Up to 50 batch-wide topic subscriptions. */
        @Nonnull
        public Builder topics(@Nullable List<AudienceTopicSubscription> topics) {
            this.topics = topics == null ? null : new ArrayList<>(topics);
            return this;
        }

        /**
         * <b>(optional)</b> When {@code true}, existing contacts have their
         * properties merged (submitted keys overwrite, absent keys are
         * preserved) and opt-outs applied. Defaults to {@code false}, in which
         * case existing contacts keep their properties but are still attached to
         * the requested lists.
         */
        @Nonnull
        public Builder updateExisting(boolean updateExisting) {
            this.updateExisting = updateExisting;
            return this;
        }

        @Nonnull
        public BulkCreateAudienceContactsOptions build() {
            boolean hasEmails = emails != null && !emails.isEmpty();
            boolean hasContacts = contacts != null && !contacts.isEmpty();

            if (!hasEmails && !hasContacts) {
                throw new IllegalArgumentException(
                        "either emails or contacts must contain at least one entry");
            }
            if (hasEmails && emails.size() > 1000) {
                throw new IllegalArgumentException("emails cannot contain more than 1000 addresses");
            }
            if (hasContacts && contacts.size() > 1000) {
                throw new IllegalArgumentException("contacts cannot contain more than 1000 rows");
            }
            if (listIds != null && listIds.size() > 50) {
                throw new IllegalArgumentException("listIds cannot contain more than 50 ids");
            }
            if (topics != null && topics.size() > 50) {
                throw new IllegalArgumentException("topics cannot contain more than 50 subscriptions");
            }
            return new BulkCreateAudienceContactsOptions(this);
        }
    }
}
