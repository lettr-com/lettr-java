package com.lettr.services.audience.contacts.model;

import javax.annotation.Nonnull;

/**
 * A topic and the subscription state to apply to it.
 *
 * <p>Used batch-wide on {@link BulkCreateAudienceContactsOptions} and per row on
 * {@link BulkAudienceContactRow}. A row-level opt-out wins over a batch-level
 * opt-in for that contact.
 *
 * <pre>{@code
 * AudienceTopicSubscription.optIn("01h-newsletter");
 * AudienceTopicSubscription.optOut("01h-promos");
 * }</pre>
 */
public class AudienceTopicSubscription {

    private final String id;

    private final AudienceTopicSubscriptionState subscription;

    private AudienceTopicSubscription(String id, AudienceTopicSubscriptionState subscription) {
        this.id = id;
        this.subscription = subscription;
    }

    /** Subscribe the contact to the topic. */
    @Nonnull
    public static AudienceTopicSubscription optIn(@Nonnull String topicId) {
        return of(topicId, AudienceTopicSubscriptionState.OPT_IN);
    }

    /**
     * Suppress the topic for the contact — including a topic that would
     * otherwise auto-subscribe newly created contacts.
     */
    @Nonnull
    public static AudienceTopicSubscription optOut(@Nonnull String topicId) {
        return of(topicId, AudienceTopicSubscriptionState.OPT_OUT);
    }

    @Nonnull
    public static AudienceTopicSubscription of(@Nonnull String topicId,
                                               @Nonnull AudienceTopicSubscriptionState subscription) {
        if (topicId == null || topicId.isEmpty()) {
            throw new IllegalArgumentException("topicId is required");
        }
        if (subscription == null) {
            throw new IllegalArgumentException("subscription is required");
        }
        return new AudienceTopicSubscription(topicId, subscription);
    }

    @Nonnull public String getId() { return id; }
    @Nonnull public AudienceTopicSubscriptionState getSubscription() { return subscription; }

    @Override
    public String toString() {
        return "AudienceTopicSubscription{id='" + id + "', subscription=" + subscription + '}';
    }
}
