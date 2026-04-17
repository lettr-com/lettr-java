package com.lettr.services.templates.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A child merge tag within a loop block.
 */
public class MergeTagChild {

    private String key;
    private String type;

    @Nonnull public String getKey() { return key; }
    /** Data type: {@code text}, {@code number}, {@code image}, or {@code button}. */
    @Nullable public String getType() { return type; }

    @Override
    public String toString() {
        return "MergeTagChild{key='" + key + "', type='" + type + "'}";
    }
}
