package com.lettr.core.util;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;

/**
 * Reads the {@link SerializedName} value of an enum constant for use in URL
 * query parameters. Lets enum classes declare each wire value exactly once
 * (on {@code @SerializedName}) instead of duplicating it in a {@code value}
 * field for query building.
 */
public final class WireValues {

    private WireValues() {}

    /**
     * Returns the {@code @SerializedName} value of the given enum constant,
     * matching how Gson serializes/deserializes it. Falls back to {@link Enum#name()}
     * if the constant has no {@code @SerializedName} annotation.
     */
    @Nonnull
    public static String of(@Nonnull Enum<?> value) {
        if (value == null) {
            throw new IllegalArgumentException("enum value is required");
        }
        try {
            Field field = value.getDeclaringClass().getField(value.name());
            SerializedName annotation = field.getAnnotation(SerializedName.class);
            return annotation != null ? annotation.value() : value.name();
        } catch (NoSuchFieldException e) {
            // Should be unreachable: Enum#name() always corresponds to a declared field.
            return value.name();
        }
    }
}
