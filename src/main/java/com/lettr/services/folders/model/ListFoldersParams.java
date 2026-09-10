package com.lettr.services.folders.model;

import com.lettr.services.templates.model.TemplatePurpose;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * Parameters for listing folders. All fields are optional.
 */
public class ListFoldersParams {

    private final Integer projectId;
    private final TemplatePurpose purpose;
    private final Integer perPage;
    private final Integer page;

    private ListFoldersParams(Builder builder) {
        this.projectId = builder.projectId;
        this.purpose = builder.purpose;
        this.perPage = builder.perPage;
        this.page = builder.page;
    }

    @Nonnull
    public static Builder builder() {
        return new Builder();
    }

    @Nonnull
    public Map<String, String> toQueryParams() {
        Map<String, String> params = new HashMap<>();
        if (projectId != null) params.put("project_id", projectId.toString());
        if (purpose != null) params.put("purpose", purpose.wireValue());
        if (perPage != null) params.put("per_page", perPage.toString());
        if (page != null) params.put("page", page.toString());
        return params;
    }

    public static class Builder {
        private Integer projectId;
        private TemplatePurpose purpose;
        private Integer perPage;
        private Integer page;

        private Builder() {}

        /**
         * <b>(optional)</b> The project to list folders from.
         *
         * <p>Without one the team's default project is used, the same way
         * {@code templates().list()} resolves it.
         */
        @Nonnull public Builder projectId(int projectId) { this.projectId = projectId; return this; }

        /** <b>(optional)</b> Narrows the list to one module. Both are returned if unset. */
        @Nonnull public Builder purpose(@Nonnull TemplatePurpose purpose) { this.purpose = purpose; return this; }

        /** <b>(optional)</b> Sets the number of results per page (1–100). */
        @Nonnull public Builder perPage(int perPage) { this.perPage = perPage; return this; }

        /** <b>(optional)</b> Sets the page number. */
        @Nonnull public Builder page(int page) { this.page = page; return this; }

        @Nonnull
        public ListFoldersParams build() {
            return new ListFoldersParams(this);
        }
    }
}
