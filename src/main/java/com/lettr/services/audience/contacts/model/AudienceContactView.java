package com.lettr.services.audience.contacts.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

/**
 * Represents an audience contact, including their list/topic memberships.
 */
public class AudienceContactView {

    private String id;
    private String email;
    private AudienceContactStatus status;
    private Map<String, String> properties;

    @SerializedName("created_at")
    private String createdAt;

    private List<ListLink> lists;
    private List<TopicLink> topics;

    @Nonnull
    public String getId() {
        return id;
    }

    @Nonnull
    public String getEmail() {
        return email;
    }

    @Nonnull
    public AudienceContactStatus getStatus() {
        return status;
    }

    @Nonnull
    public Map<String, String> getProperties() {
        return properties;
    }

    @Nonnull
    public String getCreatedAt() {
        return createdAt;
    }

    @Nonnull
    public List<ListLink> getLists() {
        return lists;
    }

    @Nonnull
    public List<TopicLink> getTopics() {
        return topics;
    }

    @Override
    public String toString() {
        return "AudienceContactView{id='" + id + "', email='" + email + "', status=" + status + '}';
    }

    /** Compact reference to a list the contact belongs to. */
    public static class ListLink {
        private String id;
        private String name;

        @Nonnull public String getId() { return id; }
        @Nonnull public String getName() { return name; }

        @Override
        public String toString() {
            return "ListLink{id='" + id + "', name='" + name + "'}";
        }
    }

    /** Compact reference to a topic the contact is subscribed to. */
    public static class TopicLink {
        private String id;
        private String name;

        @Nonnull public String getId() { return id; }
        @Nonnull public String getName() { return name; }

        @Override
        public String toString() {
            return "TopicLink{id='" + id + "', name='" + name + "'}";
        }
    }
}
