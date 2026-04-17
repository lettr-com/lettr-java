package com.lettr.services.templates;

import com.lettr.core.exception.LettrException;
import com.lettr.services.BaseService;
import com.lettr.services.templates.model.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Service for managing email templates via the Lettr API.
 */
public class Templates extends BaseService {

    public Templates(@Nonnull String apiKey) {
        super(apiKey);
    }

    /**
     * List templates with optional filtering and pagination.
     *
     * @param params optional query parameters; pass null for defaults
     */
    @Nonnull
    public ListTemplatesResponse list(@Nullable ListTemplatesParams params) throws LettrException {
        return httpClient.get("/templates", params != null ? params.toQueryParams() : null, ListTemplatesResponse.class);
    }

    /** List templates with default pagination. */
    @Nonnull
    public ListTemplatesResponse list() throws LettrException {
        return list(null);
    }

    /**
     * Get a template by slug.
     *
     * @throws IllegalArgumentException if {@code slug} is null or empty
     */
    @Nonnull
    public TemplateDetail get(@Nonnull String slug) throws LettrException {
        return get(slug, null);
    }

    /**
     * Get a template by slug within a specific project.
     *
     * @param projectId optional project ID; null uses the team's default project
     * @throws IllegalArgumentException if {@code slug} is null or empty
     */
    @Nonnull
    public TemplateDetail get(@Nonnull String slug, @Nullable Integer projectId) throws LettrException {
        if (slug == null || slug.isEmpty()) {
            throw new IllegalArgumentException("slug is required");
        }
        Map<String, String> params = null;
        if (projectId != null) {
            params = new LinkedHashMap<>();
            params.put("project_id", projectId.toString());
        }
        return httpClient.get("/templates/" + slug, params, TemplateDetail.class);
    }

    /** Create a new email template. */
    @Nonnull
    public CreateTemplateResponse create(@Nonnull CreateTemplateOptions options) throws LettrException {
        return httpClient.post("/templates", options, CreateTemplateResponse.class);
    }

    /**
     * Update an existing template.
     *
     * @throws IllegalArgumentException if {@code slug} is null or empty
     */
    @Nonnull
    public UpdateTemplateResponse update(@Nonnull String slug, @Nonnull UpdateTemplateOptions options) throws LettrException {
        if (slug == null || slug.isEmpty()) {
            throw new IllegalArgumentException("slug is required");
        }
        return httpClient.put("/templates/" + slug, options, UpdateTemplateResponse.class);
    }

    /**
     * Delete a template by slug.
     *
     * @throws IllegalArgumentException if {@code slug} is null or empty
     */
    public void delete(@Nonnull String slug) throws LettrException {
        delete(slug, null);
    }

    /**
     * Delete a template by slug within a specific project.
     *
     * @throws IllegalArgumentException if {@code slug} is null or empty
     */
    public void delete(@Nonnull String slug, @Nullable Integer projectId) throws LettrException {
        if (slug == null || slug.isEmpty()) {
            throw new IllegalArgumentException("slug is required");
        }
        Map<String, String> params = null;
        if (projectId != null) {
            params = new LinkedHashMap<>();
            params.put("project_id", projectId.toString());
        }
        httpClient.delete("/templates/" + slug, params);
    }

    /**
     * Get merge tags for a template.
     *
     * @throws IllegalArgumentException if {@code slug} is null or empty
     */
    @Nonnull
    public GetMergeTagsResponse getMergeTags(@Nonnull String slug) throws LettrException {
        return getMergeTags(slug, null);
    }

    /**
     * Get merge tags for a template with optional parameters.
     *
     * @throws IllegalArgumentException if {@code slug} is null or empty
     */
    @Nonnull
    public GetMergeTagsResponse getMergeTags(@Nonnull String slug, @Nullable GetMergeTagsParams params) throws LettrException {
        if (slug == null || slug.isEmpty()) {
            throw new IllegalArgumentException("slug is required");
        }
        return httpClient.get("/templates/" + slug + "/merge-tags",
                params != null ? params.toQueryParams() : null, GetMergeTagsResponse.class);
    }

    /** Get the rendered HTML for a template. */
    @Nonnull
    public GetTemplateHtmlResponse getHtml(@Nonnull GetTemplateHtmlParams params) throws LettrException {
        return httpClient.get("/templates/html", params.toQueryParams(), GetTemplateHtmlResponse.class);
    }
}
