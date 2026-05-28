package com.lettr.core.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArgsTest {

    @Test
    void requireNonEmptyAcceptsNonEmptyAndReturnsValue() {
        assertEquals("hi", Args.requireNonEmpty("name", "hi"));
    }

    @Test
    void requireNonEmptyRejectsNullAndEmpty() {
        IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class,
                () -> Args.requireNonEmpty("listId", null));
        assertEquals("listId is required", ex1.getMessage());

        IllegalArgumentException ex2 = assertThrows(IllegalArgumentException.class,
                () -> Args.requireNonEmpty("listId", ""));
        assertEquals("listId is required", ex2.getMessage());
    }

    @Test
    void requireNonNullAcceptsAndReturnsValue() {
        Object o = new Object();
        assertSame(o, Args.requireNonNull("options", o));
    }

    @Test
    void requireNonNullRejectsNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> Args.requireNonNull("options", null));
        assertEquals("options is required", ex.getMessage());
    }
}
