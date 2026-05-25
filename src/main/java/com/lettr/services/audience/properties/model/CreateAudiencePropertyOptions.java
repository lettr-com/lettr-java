package com.lettr.services.audience.properties.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Request body for creating an audience property. {@code name} and {@code type}
 * are immutable after creation.
 */
public class CreateAudiencePropertyOptions {

    private final String name;
    private final AudiencePropertyType type;

    @SerializedName("fallback_value")
    private final String fallbackValue;

    private CreateAudiencePropertyOptions(Builder builder) {
        this.name = builder.name;
        this.type = builder.type;
        this.fallbackValue = builder.fallbackValue;
    }

    @Nonnull
    public static Builder builder() {
        return new Builder();
    }

    @Nonnull public String getName() { return name; }
    @Nonnull public AudiencePropertyType getType() { return type; }
    @Nullable public String getFallbackValue() { return fallbackValue; }

    public static class Builder {
        private String name;
        private AudiencePropertyType type;
        private String fallbackValue;

        private Builder() {}

        /** <b>(required)</b> Property name. Must match {@code ^[a-z][a-z0-9_]*$}. */
        @Nonnull
        public Builder name(@Nonnull String name) {
            this.name = name;
            return this;
        }

        /** <b>(required)</b> Property data type. */
        @Nonnull
        public Builder type(@Nonnull AudiencePropertyType type) {
            this.type = type;
            return this;
        }

        /** <b>(optional)</b> Fallback value when the property is not set on a contact. */
        @Nonnull
        public Builder fallbackValue(@Nullable String fallbackValue) {
            this.fallbackValue = fallbackValue;
            return this;
        }

        @Nonnull
        public CreateAudiencePropertyOptions build() {
            if (name == null || name.isEmpty()) {
                throw new IllegalArgumentException("name is required");
            }
            if (type == null) {
                throw new IllegalArgumentException("type is required");
            }
            return new CreateAudiencePropertyOptions(this);
        }
    }
}
