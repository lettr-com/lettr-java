package com.lettr.services.audience.properties.model;

import com.google.gson.annotations.JsonAdapter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Request body for updating an audience property. Only {@code fallbackValue}
 * can be changed — {@code name} and {@code type} are immutable.
 *
 * <p>Passing {@code null} to {@link #withFallbackValue(String)} clears the
 * fallback by sending {@code "fallback_value": null} on the wire.</p>
 */
@JsonAdapter(UpdateAudiencePropertyOptionsAdapter.class)
public class UpdateAudiencePropertyOptions {

    private final String fallbackValue;

    private UpdateAudiencePropertyOptions(String fallbackValue) {
        this.fallbackValue = fallbackValue;
    }

    /**
     * Build with an explicit fallback value. Passing {@code null} clears the
     * existing fallback (the wire request sends {@code "fallback_value": null}).
     */
    @Nonnull
    public static UpdateAudiencePropertyOptions withFallbackValue(@Nullable String fallbackValue) {
        return new UpdateAudiencePropertyOptions(fallbackValue);
    }

    @Nullable
    public String getFallbackValue() {
        return fallbackValue;
    }
}
