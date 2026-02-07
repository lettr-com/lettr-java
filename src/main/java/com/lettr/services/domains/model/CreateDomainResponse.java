package com.lettr.services.domains.model;

import com.google.gson.annotations.SerializedName;

/**
 * Response returned after creating a new sending domain.
 */
public class CreateDomainResponse {

    private String domain;
    private String status;

    @SerializedName("status_label")
    private String statusLabel;

    private DkimInfo dkim;

    public String getDomain() { return domain; }
    public String getStatus() { return status; }
    public String getStatusLabel() { return statusLabel; }
    public DkimInfo getDkim() { return dkim; }

    /**
     * DKIM configuration for the newly created domain.
     */
    public static class DkimInfo {

        @SerializedName("public")
        private String publicKey;

        private String selector;
        private String headers;

        public String getPublicKey() { return publicKey; }
        public String getSelector() { return selector; }
        public String getHeaders() { return headers; }
    }

    @Override
    public String toString() {
        return "CreateDomainResponse{" +
                "domain='" + domain + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
