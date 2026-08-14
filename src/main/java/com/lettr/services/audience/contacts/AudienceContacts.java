package com.lettr.services.audience.contacts;

import com.lettr.core.exception.ContactAlreadyExistsException;
import com.lettr.core.exception.LettrApiException;
import com.lettr.core.exception.LettrException;
import com.lettr.services.BaseService;
import com.lettr.services.audience.contacts.model.AudienceContactView;
import com.lettr.services.audience.contacts.model.BulkAttachContactsResponse;
import com.lettr.services.audience.contacts.model.BulkContactListsOptions;
import com.lettr.services.audience.contacts.model.BulkContactTopicsOptions;
import com.lettr.services.audience.contacts.model.BulkCreateAudienceContactsOptions;
import com.lettr.services.audience.contacts.model.BulkCreateAudienceContactsResponse;
import com.lettr.services.audience.contacts.model.BulkDetachContactsResponse;
import com.lettr.services.audience.contacts.model.BulkSubscribeContactsResponse;
import com.lettr.services.audience.contacts.model.BulkUnsubscribeContactsResponse;
import com.lettr.services.audience.contacts.model.CreateAudienceContactOptions;
import com.lettr.services.audience.contacts.model.ListAudienceContactsParams;
import com.lettr.services.audience.contacts.model.ListAudienceContactsResponse;
import com.lettr.services.audience.contacts.model.UpdateAudienceContactOptions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Service for managing audience contacts and their list/topic memberships.
 */
public class AudienceContacts extends BaseService {

    /**
     * The only documented 409 on {@code POST /audience/contacts} is a duplicate
     * email. Any other conflict code the API grows later stays generic.
     */
    private static final String RESOURCE_ALREADY_EXISTS = "resource_already_exists";

    public AudienceContacts(@Nonnull String apiKey) {
        super(apiKey);
    }

    /** List contacts with default pagination. */
    @Nonnull
    public ListAudienceContactsResponse list() throws LettrException {
        return list(null);
    }

    /** List contacts with optional pagination and filters. */
    @Nonnull
    public ListAudienceContactsResponse list(@Nullable ListAudienceContactsParams params) throws LettrException {
        return httpClient.get("/audience/contacts",
                params != null ? params.toQueryParams() : null,
                ListAudienceContactsResponse.class);
    }

    /** Retrieve a single contact. */
    @Nonnull
    public AudienceContactView get(@Nonnull String contactId) throws LettrException {
        if (contactId == null || contactId.isEmpty()) {
            throw new IllegalArgumentException("contactId is required");
        }
        return httpClient.get("/audience/contacts/" + contactId, null, AudienceContactView.class);
    }

    /**
     * Create a single contact (optionally with double opt-in).
     *
     * @throws ContactAlreadyExistsException when the email is already in the
     *         team's audience. It extends {@link LettrApiException}, so existing
     *         handlers keep catching it. Do not retry — update the existing
     *         contact instead, or use {@link #bulkCreate} with
     *         {@code updateExisting(true)}.
     */
    @Nonnull
    public AudienceContactView create(@Nonnull CreateAudienceContactOptions options) throws LettrException {
        if (options == null) {
            throw new IllegalArgumentException("options is required");
        }
        try {
            return httpClient.post("/audience/contacts", options, AudienceContactView.class);
        } catch (LettrApiException e) {
            if (e.getStatusCode() == 409
                    && (e.getErrorCode() == null || RESOURCE_ALREADY_EXISTS.equals(e.getErrorCode()))) {
                throw new ContactAlreadyExistsException(
                        e.getMessage(), e.getStatusCode(), e.getErrorCode(), options.getEmail());
            }
            throw e;
        }
    }

    /**
     * Bulk create up to 1000 contacts.
     *
     * <p>Rows that fail validation are skipped, not fatal: the call still
     * returns HTTP 201 and reports them in the response's {@code errors}. Check
     * {@link BulkCreateAudienceContactsResponse#hasErrors()} — a call that
     * returns without throwing does not mean every row landed.
     */
    @Nonnull
    public BulkCreateAudienceContactsResponse bulkCreate(@Nonnull BulkCreateAudienceContactsOptions options) throws LettrException {
        if (options == null) {
            throw new IllegalArgumentException("options is required");
        }
        return httpClient.post("/audience/contacts/bulk", options, BulkCreateAudienceContactsResponse.class);
    }

    /** Partially update a contact. */
    @Nonnull
    public AudienceContactView update(@Nonnull String contactId, @Nonnull UpdateAudienceContactOptions options) throws LettrException {
        if (contactId == null || contactId.isEmpty()) {
            throw new IllegalArgumentException("contactId is required");
        }
        if (options == null) {
            throw new IllegalArgumentException("options is required");
        }
        return httpClient.patch("/audience/contacts/" + contactId, options, AudienceContactView.class);
    }

    /** Permanently delete a contact. */
    public void delete(@Nonnull String contactId) throws LettrException {
        if (contactId == null || contactId.isEmpty()) {
            throw new IllegalArgumentException("contactId is required");
        }
        httpClient.delete("/audience/contacts/" + contactId);
    }

    /** Bulk attach contacts to lists (cartesian product of contactIds × listIds). */
    @Nonnull
    public BulkAttachContactsResponse bulkAttachToLists(@Nonnull BulkContactListsOptions options) throws LettrException {
        if (options == null) {
            throw new IllegalArgumentException("options is required");
        }
        return httpClient.post("/audience/contacts/lists/bulk", options, BulkAttachContactsResponse.class);
    }

    /** Bulk detach contacts from lists (cartesian product of contactIds × listIds). */
    @Nonnull
    public BulkDetachContactsResponse bulkDetachFromLists(@Nonnull BulkContactListsOptions options) throws LettrException {
        if (options == null) {
            throw new IllegalArgumentException("options is required");
        }
        return httpClient.delete("/audience/contacts/lists/bulk", options, BulkDetachContactsResponse.class);
    }

    /** Attach a single contact to a single list. Idempotent. */
    public void attachToList(@Nonnull String contactId, @Nonnull String listId) throws LettrException {
        if (contactId == null || contactId.isEmpty()) {
            throw new IllegalArgumentException("contactId is required");
        }
        if (listId == null || listId.isEmpty()) {
            throw new IllegalArgumentException("listId is required");
        }
        httpClient.post("/audience/contacts/" + contactId + "/lists/" + listId, null);
    }

    /** Detach a single contact from a single list. Idempotent. */
    public void detachFromList(@Nonnull String contactId, @Nonnull String listId) throws LettrException {
        if (contactId == null || contactId.isEmpty()) {
            throw new IllegalArgumentException("contactId is required");
        }
        if (listId == null || listId.isEmpty()) {
            throw new IllegalArgumentException("listId is required");
        }
        httpClient.delete("/audience/contacts/" + contactId + "/lists/" + listId);
    }

    /** Subscribe a contact to a topic. Idempotent. */
    public void subscribeToTopic(@Nonnull String contactId, @Nonnull String topicId) throws LettrException {
        if (contactId == null || contactId.isEmpty()) {
            throw new IllegalArgumentException("contactId is required");
        }
        if (topicId == null || topicId.isEmpty()) {
            throw new IllegalArgumentException("topicId is required");
        }
        httpClient.post("/audience/contacts/" + contactId + "/topics/" + topicId, null);
    }

    /**
     * Bulk subscribe contacts to topics (cartesian product of contactIds × topicIds,
     * up to 1000 × 50).
     *
     * <p>Pass {@link BulkCreateAudienceContactsResponse#getContactIds()} from a
     * bulk create — no id lookup needed.
     */
    @Nonnull
    public BulkSubscribeContactsResponse bulkSubscribeToTopics(@Nonnull BulkContactTopicsOptions options) throws LettrException {
        if (options == null) {
            throw new IllegalArgumentException("options is required");
        }
        return httpClient.post("/audience/contacts/topics/bulk", options, BulkSubscribeContactsResponse.class);
    }

    /**
     * Bulk unsubscribe contacts from topics (cartesian product of
     * contactIds × topicIds). Pairs that do not exist are ignored.
     *
     * <p>This is a DELETE carrying a request body, as
     * {@link #bulkDetachFromLists} already is.
     */
    @Nonnull
    public BulkUnsubscribeContactsResponse bulkUnsubscribeFromTopics(@Nonnull BulkContactTopicsOptions options) throws LettrException {
        if (options == null) {
            throw new IllegalArgumentException("options is required");
        }
        return httpClient.delete("/audience/contacts/topics/bulk", options, BulkUnsubscribeContactsResponse.class);
    }

    /** Unsubscribe a contact from a topic. Idempotent. */
    public void unsubscribeFromTopic(@Nonnull String contactId, @Nonnull String topicId) throws LettrException {
        if (contactId == null || contactId.isEmpty()) {
            throw new IllegalArgumentException("contactId is required");
        }
        if (topicId == null || topicId.isEmpty()) {
            throw new IllegalArgumentException("topicId is required");
        }
        httpClient.delete("/audience/contacts/" + contactId + "/topics/" + topicId);
    }
}
