package com.lettr.core.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IdempotencyKeysTest {

    @Test
    void acceptsTheDocumentedFormat() {
        assertTrue(IdempotencyKeys.isValid("order-confirmation-12345"));
        assertTrue(IdempotencyKeys.isValid("a.b_c-1"));
        assertTrue(IdempotencyKeys.isValid("a"));
        assertTrue(IdempotencyKeys.isValid("a".repeat(255)));
    }

    @Test
    void rejectsAnythingElse() {
        assertFalse(IdempotencyKeys.isValid(null));
        assertFalse(IdempotencyKeys.isValid(""));
        assertFalse(IdempotencyKeys.isValid("order 123"));
        assertFalse(IdempotencyKeys.isValid("order/123"));
        assertFalse(IdempotencyKeys.isValid("order:123"));
        assertFalse(IdempotencyKeys.isValid("order-č"));
        assertFalse(IdempotencyKeys.isValid("a".repeat(256)));
    }

    /** Validated locally so a bad key costs no round trip and no 422. */
    @Test
    void validateThrowsForAMalformedKey() {
        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> IdempotencyKeys.validate("order 123"));

        assertTrue(e.getMessage().contains("1 to 255"));
    }

    @Test
    void validateAcceptsAGoodKey() {
        assertDoesNotThrow(() -> IdempotencyKeys.validate("order-12345"));
    }
}
