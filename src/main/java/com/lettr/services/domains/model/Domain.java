package com.lettr.services.domains.model;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a sending domain registered with Lettr.
 */
public class Domain {

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

    @SerializedName("tracking_domain")
    private String trackingDomain;

    private DnsRecords dns;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;

    public String getDomain() { return domain; }
    public String getStatus() { return status; }
    public String getStatusLabel() { return statusLabel; }
    public boolean isCanSend() { return canSend; }
    public String getCnameStatus() { return cnameStatus; }
    public String getDkimStatus() { return dkimStatus; }
    public String getTrackingDomain() { return trackingDomain; }
    public DnsRecords getDns() { return dns; }
    public String getCreatedAt() { return createdAt; }
    public String getUpdatedAt() { return updatedAt; }

    /**
     * DNS records associated with the domain.
     */
    public static class DnsRecords {
        private DkimRecord dkim;

        public DkimRecord getDkim() { return dkim; }

        public static class DkimRecord {
            private String selector;

            @SerializedName("public")
            private String publicKey;

            public String getSelector() { return selector; }
            public String getPublicKey() { return publicKey; }
        }
    }

    @Override
    public String toString() {
        return "Domain{" +
                "domain='" + domain + '\'' +
                ", status='" + status + '\'' +
                ", canSend=" + canSend +
                '}';
    }
}
