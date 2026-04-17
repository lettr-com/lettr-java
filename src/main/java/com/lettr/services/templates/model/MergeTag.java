package com.lettr.services.templates.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * A merge tag extracted from template content.
 */
public class MergeTag {

    private String key;
    private boolean required;
    private String type;
    private List<MergeTagChild> children;

    @Nonnull public String getKey() { return key; }
    public boolean isRequired() { return required; }
    /** Data type ({@code text}, {@code number}, {@code image}, {@code button}). Only present for loop children. */
    @Nullable public String getType() { return type; }
    /** Child fields available within a loop iteration. Null for non-loop merge tags. */
    @Nullable public List<MergeTagChild> getChildren() { return children; }

    @Override
    public String toString() {
        return "MergeTag{key='" + key + "', required=" + required + '}';
    }
}
