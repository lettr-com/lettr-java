package com.lettr.services.audience.lists;

import com.lettr.core.exception.LettrException;
import com.lettr.services.BaseService;
import com.lettr.services.audience.lists.model.AudienceListView;
import com.lettr.services.audience.lists.model.BulkDeleteAudienceListsOptions;
import com.lettr.services.audience.lists.model.BulkDeleteAudienceListsResponse;
import com.lettr.services.audience.lists.model.CreateAudienceListOptions;
import com.lettr.services.audience.lists.model.ListAudienceListsResponse;
import com.lettr.services.audience.lists.model.UpdateAudienceListOptions;
import com.lettr.services.audience.model.PageParams;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Service for managing audience lists.
 */
public class AudienceLists extends BaseService {

    public AudienceLists(@Nonnull String apiKey) {
        super(apiKey);
    }

    /** List audience lists with default pagination. */
    @Nonnull
    public ListAudienceListsResponse list() throws LettrException {
        return list(null);
    }

    /** List audience lists with optional pagination. */
    @Nonnull
    public ListAudienceListsResponse list(@Nullable PageParams params) throws LettrException {
        return httpClient.get("/audience/lists",
                params != null ? params.toQueryParams() : null,
                ListAudienceListsResponse.class);
    }

    /** Retrieve a single audience list. */
    @Nonnull
    public AudienceListView get(@Nonnull String listId) throws LettrException {
        if (listId == null || listId.isEmpty()) {
            throw new IllegalArgumentException("listId is required");
        }
        return httpClient.get("/audience/lists/" + listId, null, AudienceListView.class);
    }

    /** Create a new audience list. */
    @Nonnull
    public AudienceListView create(@Nonnull CreateAudienceListOptions options) throws LettrException {
        if (options == null) {
            throw new IllegalArgumentException("options is required");
        }
        return httpClient.post("/audience/lists", options, AudienceListView.class);
    }

    /** Partially update an audience list. */
    @Nonnull
    public AudienceListView update(@Nonnull String listId, @Nonnull UpdateAudienceListOptions options) throws LettrException {
        if (listId == null || listId.isEmpty()) {
            throw new IllegalArgumentException("listId is required");
        }
        if (options == null) {
            throw new IllegalArgumentException("options is required");
        }
        return httpClient.patch("/audience/lists/" + listId, options, AudienceListView.class);
    }

    /** Permanently delete an audience list. */
    public void delete(@Nonnull String listId) throws LettrException {
        if (listId == null || listId.isEmpty()) {
            throw new IllegalArgumentException("listId is required");
        }
        httpClient.delete("/audience/lists/" + listId);
    }

    /** Bulk delete up to 50 audience lists in a single request. */
    @Nonnull
    public BulkDeleteAudienceListsResponse bulkDelete(@Nonnull BulkDeleteAudienceListsOptions options) throws LettrException {
        if (options == null) {
            throw new IllegalArgumentException("options is required");
        }
        return httpClient.delete("/audience/lists/bulk", options, BulkDeleteAudienceListsResponse.class);
    }
}
