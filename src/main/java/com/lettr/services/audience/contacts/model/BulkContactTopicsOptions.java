package com.lettr.services.audience.contacts.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Shared request body for bulk subscribing or unsubscribing contacts and topics.
 * The endpoint applies the cartesian product of {@code contactIds} × {@code topicIds}.
 *
 * <p>A single instance serves both directions — see
 * {@code AudienceContacts.bulkSubscribeToTopics} and
 * {@code AudienceContacts.bulkUnsubscribeFromTopics}. Feed it
 * {@link BulkCreateAudienceContactsResponse#getContactIds()} from a bulk create
 * and no id lookup is needed.
 */
public class BulkContactTopicsOptions {

    @SerializedName("contact_ids")
    private final List<String> contactIds;

    @SerializedName("topic_ids")
    private final List<String> topicIds;

    private BulkContactTopicsOptions(List<String> contactIds, List<String> topicIds) {
        this.contactIds = contactIds;
        this.topicIds = topicIds;
    }

    @Nonnull
    public static BulkContactTopicsOptions of(@Nonnull List<String> contactIds, @Nonnull List<String> topicIds) {
        if (contactIds == null || contactIds.isEmpty()) {
            throw new IllegalArgumentException("contactIds must contain at least one id");
        }
        if (contactIds.size() > 1000) {
            throw new IllegalArgumentException("contactIds cannot contain more than 1000 ids");
        }
        if (topicIds == null || topicIds.isEmpty()) {
            throw new IllegalArgumentException("topicIds must contain at least one id");
        }
        if (topicIds.size() > 50) {
            throw new IllegalArgumentException("topicIds cannot contain more than 50 ids");
        }
        return new BulkContactTopicsOptions(new ArrayList<>(contactIds), new ArrayList<>(topicIds));
    }

    @Nonnull public List<String> getContactIds() { return contactIds; }
    @Nonnull public List<String> getTopicIds() { return topicIds; }
}
