package com.lettr.services.audience.topics.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AudienceTopicView {

    private String id;
    private String name;
    private String description;

    @SerializedName("default_subscription")
    private AudienceTopicDefaultSubscription defaultSubscription;

    private AudienceTopicVisibility visibility;

    @SerializedName("contacts_count")
    private int contactsCount;

    @SerializedName("created_at")
    private String createdAt;

    @Nonnull public String getId() { return id; }
    @Nonnull public String getName() { return name; }
    @Nullable public String getDescription() { return description; }
    @Nonnull public AudienceTopicDefaultSubscription getDefaultSubscription() { return defaultSubscription; }
    @Nonnull public AudienceTopicVisibility getVisibility() { return visibility; }
    public int getContactsCount() { return contactsCount; }
    @Nonnull public String getCreatedAt() { return createdAt; }

    @Override
    public String toString() {
        return "AudienceTopicView{id='" + id + "', name='" + name + "', visibility=" + visibility + '}';
    }
}
