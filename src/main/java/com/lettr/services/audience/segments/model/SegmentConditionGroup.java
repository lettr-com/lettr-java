package com.lettr.services.audience.segments.model;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A group of conditions joined by OR. Multiple groups in a segment are joined by AND.
 */
public class SegmentConditionGroup {

    private final List<SegmentCondition> conditions;

    private SegmentConditionGroup(List<SegmentCondition> conditions) {
        this.conditions = conditions;
    }

    @Nonnull
    public static SegmentConditionGroup of(@Nonnull List<SegmentCondition> conditions) {
        if (conditions == null || conditions.isEmpty()) {
            throw new IllegalArgumentException("conditions must contain at least one item");
        }
        return new SegmentConditionGroup(new ArrayList<>(conditions));
    }

    @Nonnull
    public static SegmentConditionGroup of(@Nonnull SegmentCondition... conditions) {
        if (conditions == null || conditions.length == 0) {
            throw new IllegalArgumentException("conditions must contain at least one item");
        }
        return new SegmentConditionGroup(new ArrayList<>(Arrays.asList(conditions)));
    }

    @Nonnull public List<SegmentCondition> getConditions() { return conditions; }
}
