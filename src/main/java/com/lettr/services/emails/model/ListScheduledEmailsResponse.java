package com.lettr.services.emails.model;

import com.google.gson.annotations.SerializedName;
import com.lettr.core.model.OffsetPagination;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

/**
 * Response from listing scheduled emails: a page of scheduled emails plus
 * pagination metadata.
 */
public class ListScheduledEmailsResponse {

    @SerializedName("scheduled_emails")
    private List<ScheduledEmail> scheduledEmails;

    private OffsetPagination pagination;

    @Nonnull
    public List<ScheduledEmail> getScheduledEmails() {
        return scheduledEmails != null ? scheduledEmails : Collections.emptyList();
    }

    @Nonnull
    public OffsetPagination getPagination() {
        return pagination;
    }

    @Override
    public String toString() {
        return "ListScheduledEmailsResponse{scheduledEmails=" + scheduledEmails + ", pagination=" + pagination + '}';
    }
}
