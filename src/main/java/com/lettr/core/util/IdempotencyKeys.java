package com.lettr.core.util;

import java.util.regex.Pattern;

/**
 * Validation for {@code Idempotency-Key} values.
 *
 * <p>The key identifies one logical send. Reuse it when you retry and the API
 * returns the original result instead of delivering a second email.
 *
 * <p><b>You choose the key; the SDK never generates one.</b> It only works if
 * both attempts use the same value, and the SDK does not retry — one
 * {@code send()} is one HTTP request — so the retry is yours, and only you know
 * that two calls are the same logical send. A key generated inside
 * {@code send()} would differ on every attempt and protect nothing while
 * looking like it did.
 */
public final class IdempotencyKeys {

    /** The format the API accepts: 1–255 characters of letters, digits, {@code . _ -}. */
    private static final Pattern PATTERN = Pattern.compile("^[A-Za-z0-9._-]{1,255}$");

    private IdempotencyKeys() {}

    /**
     * Whether a string is a usable idempotency key.
     *
     * <p>Exported so callers deriving keys from their own ids — an order
     * number, a job id — can check before sending rather than discovering it as
     * a 422.
     */
    public static boolean isValid(String key) {
        return key != null && PATTERN.matcher(key).matches();
    }

    /**
     * Throws {@link IllegalArgumentException} if the key is malformed.
     *
     * <p>Checked locally so a bad key fails on your machine instead of costing
     * a round trip and a 422.
     */
    public static void validate(String key) {
        if (!isValid(key)) {
            throw new IllegalArgumentException(
                    "Invalid idempotency key: use 1 to 255 letters, digits, periods, underscores or hyphens");
        }
    }
}
