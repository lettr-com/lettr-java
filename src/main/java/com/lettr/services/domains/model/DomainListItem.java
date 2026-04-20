package com.lettr.services.domains.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Slim view of a sending domain as returned by {@code GET /domains}.
 * For the full detail view (DKIM/SPF/DMARC, DNS records, provider), see {@link Domain}.
 */
public class DomainListItem {

    private String domain;
    private String status;

    @SerializedName("status_label")
    private String statusLabel;

    @SerializedName("can_send")
    private boolean canSend;

    @SerializedName("cname_status")
    private String cnameStatus;

    @SerializedName("dkim_status")
    private String dkimStatus;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;

    @Nonnull public String getDomain() { return domain; }
    /** Status: {@code pending}, {@code approved}, or {@code blocked}. */
    @Nonnull public String getStatus() { return status; }
    @Nonnull public String getStatusLabel() { return statusLabel; }
    public boolean isCanSend() { return canSend; }
    @Nullable public String getCnameStatus() { return cnameStatus; }
    /** DKIM status. Null until the domain has been verified at least once. */
    @Nullable public String getDkimStatus() { return dkimStatus; }
    @Nonnull public String getCreatedAt() { return createdAt; }
    @Nonnull public String getUpdatedAt() { return updatedAt; }

    @Override
    public String toString() {
        return "DomainListItem{domain='" + domain + "', status='" + status + "', canSend=" + canSend + '}';
    }
}
