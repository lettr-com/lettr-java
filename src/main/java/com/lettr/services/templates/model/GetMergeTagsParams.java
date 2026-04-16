package com.lettr.services.templates.model;

import javax.annotation.Nonnull;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Parameters for getting merge tags of a template. All fields are optional.
 */
public class GetMergeTagsParams {

    private final Integer projectId;
    private final Integer version;

    private GetMergeTagsParams(Builder builder) {
        this.projectId = builder.projectId;
        this.version = builder.version;
    }

    @Nonnull
    public static Builder builder() {
        return new Builder();
    }

    @Nonnull
    public Map<String, String> toQueryParams() {
        Map<String, String> params = new LinkedHashMap<>();
        if (projectId != null) params.put("project_id", projectId.toString());
        if (version != null) params.put("version", version.toString());
        return params;
    }

    public static class Builder {
        private Integer projectId;
        private Integer version;

        private Builder() {}

        /** <b>(optional)</b> Sets the project ID. Defaults to the team's default project. */
        @Nonnull public Builder projectId(int projectId) { this.projectId = projectId; return this; }

        /** <b>(optional)</b> Sets the template version. Defaults to the active version. */
        @Nonnull public Builder version(int version) { this.version = version; return this; }

        @Nonnull
        public GetMergeTagsParams build() {
            return new GetMergeTagsParams(this);
        }
    }
}
