package com.lettr.core.util;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Argument validation helpers used at SDK entry points to reject obviously
 * invalid input early, with a consistent {@code "<name> is required"} message.
 */
public final class Args {

    private Args() {}

    /**
     * Throws {@link IllegalArgumentException} if {@code value} is {@code null}
     * or empty.
     *
     * @param name  the parameter name, used in the exception message
     * @param value the value to check
     * @return {@code value} unchanged, so this can be used as an expression
     */
    @Nonnull
    public static String requireNonEmpty(@Nonnull String name, @Nullable String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException(name + " is required");
        }
        return value;
    }

    /**
     * Throws {@link IllegalArgumentException} if {@code value} is {@code null}.
     */
    @Nonnull
    public static <T> T requireNonNull(@Nonnull String name, @Nullable T value) {
        if (value == null) {
            throw new IllegalArgumentException(name + " is required");
        }
        return value;
    }
}
