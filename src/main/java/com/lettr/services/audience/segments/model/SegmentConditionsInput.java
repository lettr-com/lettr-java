package com.lettr.services.audience.segments.model;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Wrapper used as the {@code conditions} field on segment create/update requests.
 * The top-level groups are joined by OR; conditions within each group are joined by AND.
 */
public class SegmentConditionsInput {

    private final List<SegmentConditionGroup> groups;

    private SegmentConditionsInput(List<SegmentConditionGroup> groups) {
        this.groups = groups;
    }

    @Nonnull
    public static SegmentConditionsInput of(@Nonnull List<SegmentConditionGroup> groups) {
        if (groups == null || groups.isEmpty()) {
            throw new IllegalArgumentException("groups must contain at least one item");
        }
        return new SegmentConditionsInput(new ArrayList<>(groups));
    }

    @Nonnull
    public static SegmentConditionsInput of(@Nonnull SegmentConditionGroup... groups) {
        if (groups == null || groups.length == 0) {
            throw new IllegalArgumentException("groups must contain at least one item");
        }
        return new SegmentConditionsInput(new ArrayList<>(Arrays.asList(groups)));
    }

    @Nonnull public List<SegmentConditionGroup> getGroups() { return groups; }
}
