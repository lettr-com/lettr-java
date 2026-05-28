package com.lettr.core.util;

import com.google.gson.annotations.SerializedName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WireValuesTest {

    enum Annotated {
        @SerializedName("kebab-case") KEBAB,
        @SerializedName("snake_case") SNAKE
    }

    enum Bare {
        FALLBACK
    }

    @Test
    void returnsSerializedNameValue() {
        assertEquals("kebab-case", WireValues.of(Annotated.KEBAB));
        assertEquals("snake_case", WireValues.of(Annotated.SNAKE));
    }

    @Test
    void fallsBackToEnumNameWhenUnannotated() {
        assertEquals("FALLBACK", WireValues.of(Bare.FALLBACK));
    }

    @Test
    void rejectsNull() {
        assertThrows(IllegalArgumentException.class, () -> WireValues.of(null));
    }
}
