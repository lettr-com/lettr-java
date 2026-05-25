package com.lettr.services.audience.lists.model;

import javax.annotation.Nonnull;

/**
 * Request body for creating an audience list. Name must be unique within the team.
 */
public class CreateAudienceListOptions {

    private final String name;

    private CreateAudienceListOptions(String name) {
        this.name = name;
    }

    @Nonnull
    public static CreateAudienceListOptions of(@Nonnull String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("name is required");
        }
        return new CreateAudienceListOptions(name);
    }

    @Nonnull
    public String getName() {
        return name;
    }
}
