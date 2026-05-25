package com.lettr.services.audience;

import com.lettr.services.audience.contacts.AudienceContacts;
import com.lettr.services.audience.lists.AudienceLists;
import com.lettr.services.audience.properties.AudienceProperties;
import com.lettr.services.audience.segments.AudienceSegments;
import com.lettr.services.audience.topics.AudienceTopics;

import javax.annotation.Nonnull;

/**
 * Entry point for the Lettr audience API. Returns sub-services for managing
 * audience lists, contacts, topics, properties, and segments.
 */
public class Audience {

    private final String apiKey;

    public Audience(@Nonnull String apiKey) {
        this.apiKey = apiKey;
    }

    /** Returns the audience lists service. */
    @Nonnull
    public AudienceLists lists() {
        return new AudienceLists(apiKey);
    }

    /** Returns the audience contacts service. */
    @Nonnull
    public AudienceContacts contacts() {
        return new AudienceContacts(apiKey);
    }

    /** Returns the audience topics service. */
    @Nonnull
    public AudienceTopics topics() {
        return new AudienceTopics(apiKey);
    }

    /** Returns the audience properties service. */
    @Nonnull
    public AudienceProperties properties() {
        return new AudienceProperties(apiKey);
    }

    /** Returns the audience segments service. */
    @Nonnull
    public AudienceSegments segments() {
        return new AudienceSegments(apiKey);
    }
}
