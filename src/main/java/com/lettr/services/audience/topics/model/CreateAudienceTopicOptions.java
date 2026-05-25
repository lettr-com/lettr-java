package com.lettr.services.audience.topics.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Request body for creating an audience topic. {@code defaultSubscription}
 * cannot be changed after creation.
 */
public class CreateAudienceTopicOptions {

    private final String name;
    private final String description;

    @SerializedName("default_subscription")
    private final AudienceTopicDefaultSubscription defaultSubscription;

    private final AudienceTopicVisibility visibility;

    private CreateAudienceTopicOptions(Builder builder) {
        this.name = builder.name;
        this.description = builder.description;
        this.defaultSubscription = builder.defaultSubscription;
        this.visibility = builder.visibility;
    }

    @Nonnull
    public static Builder builder() {
        return new Builder();
    }

    @Nonnull public String getName() { return name; }
    @Nullable public String getDescription() { return description; }
    @Nullable public AudienceTopicDefaultSubscription getDefaultSubscription() { return defaultSubscription; }
    @Nullable public AudienceTopicVisibility getVisibility() { return visibility; }

    public static class Builder {
        private String name;
        private String description;
        private AudienceTopicDefaultSubscription defaultSubscription;
        private AudienceTopicVisibility visibility;

        private Builder() {}

        /** <b>(required)</b> Topic name (max 255). */
        @Nonnull
        public Builder name(@Nonnull String name) {
            this.name = name;
            return this;
        }

        /** <b>(optional)</b> Topic description (max 1000). */
        @Nonnull
        public Builder description(@Nullable String description) {
            this.description = description;
            return this;
        }

        /** <b>(optional)</b> Default subscription policy (default {@code OPT_IN}). Immutable after creation. */
        @Nonnull
        public Builder defaultSubscription(@Nullable AudienceTopicDefaultSubscription defaultSubscription) {
            this.defaultSubscription = defaultSubscription;
            return this;
        }

        /** <b>(optional)</b> Topic visibility (default {@code PRIVATE}). */
        @Nonnull
        public Builder visibility(@Nullable AudienceTopicVisibility visibility) {
            this.visibility = visibility;
            return this;
        }

        @Nonnull
        public CreateAudienceTopicOptions build() {
            if (name == null || name.isEmpty()) {
                throw new IllegalArgumentException("name is required");
            }
            return new CreateAudienceTopicOptions(this);
        }
    }
}
