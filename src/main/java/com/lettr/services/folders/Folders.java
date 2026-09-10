package com.lettr.services.folders;

import com.lettr.core.exception.LettrException;
import com.lettr.services.BaseService;
import com.lettr.services.folders.model.ListFoldersParams;
import com.lettr.services.folders.model.ListFoldersResponse;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Service for listing the folders templates are filed into.
 *
 * <p>Read-only: creating, renaming and deleting folders stay in the app,
 * because deleting one moves or deletes the templates inside it.
 *
 * <p>This is what {@code CreateTemplateOptions.folderId()} was missing —
 * nothing else in the SDK returns a folder id, so a caller either omitted it
 * and accepted whichever folder the API picked, or hardcoded an integer read
 * out of an app URL.
 */
public class Folders extends BaseService {

    public Folders(@Nonnull String apiKey) {
        super(apiKey);
    }

    /**
     * List folders with optional filtering and pagination.
     *
     * @param params optional query parameters; pass null for defaults
     * @throws LettrException if the project is not found or belongs to another team
     */
    @Nonnull
    public ListFoldersResponse list(@Nullable ListFoldersParams params) throws LettrException {
        return httpClient.get("/folders", params != null ? params.toQueryParams() : null, ListFoldersResponse.class);
    }

    /** List folders in the team's default project. */
    @Nonnull
    public ListFoldersResponse list() throws LettrException {
        return list(null);
    }
}
