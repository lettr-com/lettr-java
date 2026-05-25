package com.lettr.services.audience.properties.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AudiencePropertyView {

    private String id;
    private String name;
    private AudiencePropertyType type;

    @SerializedName("fallback_value")
    private String fallbackValue;

    @SerializedName("created_at")
    private String createdAt;

    @Nonnull public String getId() { return id; }
    @Nonnull public String getName() { return name; }
    @Nonnull public AudiencePropertyType getType() { return type; }
    @Nullable public String getFallbackValue() { return fallbackValue; }
    @Nonnull public String getCreatedAt() { return createdAt; }

    @Override
    public String toString() {
        return "AudiencePropertyView{id='" + id + "', name='" + name + "', type=" + type + '}';
    }
}
