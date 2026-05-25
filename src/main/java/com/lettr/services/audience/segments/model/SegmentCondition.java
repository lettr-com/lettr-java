package com.lettr.services.audience.segments.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A single condition inside a segment. {@code value} is required for most
 * operators; {@code IS_TRUE} and {@code IS_FALSE} omit it.
 */
public class SegmentCondition {

    private final String field;
    private final SegmentOperator operator;
    private final String value;

    private SegmentCondition(String field, SegmentOperator operator, String value) {
        this.field = field;
        this.operator = operator;
        this.value = value;
    }

    /** Create a condition that uses a value (e.g. equals, contains, before). */
    @Nonnull
    public static SegmentCondition of(@Nonnull String field, @Nonnull SegmentOperator operator, @Nullable String value) {
        if (field == null || field.isEmpty()) {
            throw new IllegalArgumentException("field is required");
        }
        if (operator == null) {
            throw new IllegalArgumentException("operator is required");
        }
        return new SegmentCondition(field, operator, value);
    }

    /** Create a value-less condition (only valid for {@code IS_TRUE} / {@code IS_FALSE}). */
    @Nonnull
    public static SegmentCondition of(@Nonnull String field, @Nonnull SegmentOperator operator) {
        return of(field, operator, null);
    }

    @Nonnull public String getField() { return field; }
    @Nonnull public SegmentOperator getOperator() { return operator; }
    @Nullable public String getValue() { return value; }
}
