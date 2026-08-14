package com.lettr.services.audience.contacts.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Result of a bulk contact create.
 *
 * <p>A bulk create can <b>partially succeed</b>: rows that fail validation are
 * skipped and reported in {@link #getErrors()}, while the rest of the batch is
 * still written. The call returns HTTP 201 either way, so a method that returns
 * without throwing does <b>not</b> mean every row landed — check
 * {@link #hasErrors()}.
 *
 * <p>{@link #getAlreadyExisted()} and {@link #getUpdated()} overlap by design.
 * They answer different questions ("was the address already in the audience?"
 * vs "did this request change the contact?"), so the counters do not sum to the
 * row count: a contact that already existed and got attached to a list is
 * counted in both.
 */
public class BulkCreateAudienceContactsResponse {

    private int created;

    @SerializedName("already_existed")
    private int alreadyExisted;

    private int updated;

    @SerializedName("error_count")
    private int errorCount;

    private List<BulkAudienceContactError> errors;

    private List<BulkAudienceContactRef> contacts;

    public int getCreated() {
        return created;
    }

    public int getAlreadyExisted() {
        return alreadyExisted;
    }

    /**
     * Existing contacts this request changed — properties merged, a list or
     * topic attached, or a subscription dropped.
     */
    public int getUpdated() {
        return updated;
    }

    /** Number of skipped rows. */
    public int getErrorCount() {
        return errorCount;
    }

    /**
     * The skipped rows. Never {@code null} — an API deployment that predates
     * TPL-2105 omits the field, which reads as an empty list here.
     */
    @Nonnull
    public List<BulkAudienceContactError> getErrors() {
        return errors == null ? Collections.emptyList() : errors;
    }

    /**
     * Every contact that exists after the request, in submission order. Never
     * {@code null}.
     */
    @Nonnull
    public List<BulkAudienceContactRef> getContacts() {
        return contacts == null ? Collections.emptyList() : contacts;
    }

    /**
     * Whether any row was skipped. Always check this — a bulk create reports
     * partial failures in the body, not in the HTTP status.
     */
    public boolean hasErrors() {
        return !getErrors().isEmpty();
    }

    /**
     * The ids of every contact that exists after the request, in submission
     * order — ready to feed into the bulk list and topic endpoints.
     */
    @Nonnull
    public List<String> getContactIds() {
        List<String> ids = new ArrayList<>();
        for (BulkAudienceContactRef contact : getContacts()) {
            ids.add(contact.getId());
        }
        return ids;
    }

    /**
     * Looks up the id for a submitted address, or {@code null} when it is not in
     * the response. Matching is case-insensitive because the API normalizes
     * addresses before storing them.
     */
    @Nullable
    public String findIdFor(@Nullable String email) {
        if (email == null) {
            return null;
        }
        String needle = email.trim().toLowerCase(Locale.ROOT);
        for (BulkAudienceContactRef contact : getContacts()) {
            if (contact.getEmail() != null && contact.getEmail().toLowerCase(Locale.ROOT).equals(needle)) {
                return contact.getId();
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "BulkCreateAudienceContactsResponse{created=" + created
                + ", alreadyExisted=" + alreadyExisted
                + ", updated=" + updated
                + ", errorCount=" + errorCount
                + ", contacts=" + getContacts().size() + '}';
    }
}
