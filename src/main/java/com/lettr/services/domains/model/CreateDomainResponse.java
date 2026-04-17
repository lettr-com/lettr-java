package com.lettr.services.domains.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Response returned after creating a new sending domain.
 */
public class CreateDomainResponse {

    private String domain;
    private String status;

    @SerializedName("status_label")
    private String statusLabel;

    private DkimInfo dkim;

    @Nonnull public String getDomain() { return domain; }
    /** Domain status: {@code pending}, {@code approved}, or {@code blocked}. */
    @Nonnull public String getStatus() { return status; }
    @Nonnull public String getStatusLabel() { return statusLabel; }
    @Nullable public DkimInfo getDkim() { return dkim; }

    /** DKIM configuration for the newly created domain. */
    public static class DkimInfo {

        @SerializedName("public")
        private String publicKey;

        private String selector;
        private String headers;

        @SerializedName("signing_domain")
        private String signingDomain;

        @Nullable public String getPublicKey() { return publicKey; }
        @Nullable public String getSelector() { return selector; }
        @Nullable public String getHeaders() { return headers; }
        /** Domain used for DKIM signing. Normally matches the top-level {@code domain}. */
        @Nullable public String getSigningDomain() { return signingDomain; }
    }

    @Override
    public String toString() {
        return "CreateDomainResponse{domain='" + domain + "', status='" + status + "'}";
    }
}
