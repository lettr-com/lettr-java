package com.lettr.services.domains.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

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

    @SerializedName("dmarc_status")
    private String dmarcStatus;

    @SerializedName("spf_status")
    private String spfStatus;

    @SerializedName("is_primary_domain")
    private Boolean isPrimaryDomain;

    @SerializedName("tracking_domain")
    private String trackingDomain;

    private DnsRecords dns;

    @SerializedName("dns_provider")
    private DnsProvider dnsProvider;

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
    @Nullable public String getDmarcStatus() { return dmarcStatus; }
    @Nullable public String getSpfStatus() { return spfStatus; }
    /** True if this is a root/apex sending domain (requires SPF instead of CNAME). */
    @Nullable public Boolean getIsPrimaryDomain() { return isPrimaryDomain; }
    @Nullable public String getTrackingDomain() { return trackingDomain; }
    @Nullable public DnsRecords getDns() { return dns; }
    /** Detected DNS provider, or null if detection hasn't run. */
    @Nullable public DnsProvider getDnsProvider() { return dnsProvider; }
    @Nonnull public String getCreatedAt() { return createdAt; }
    @Nonnull public String getUpdatedAt() { return updatedAt; }

    /** DNS records associated with the domain. */
    public static class DnsRecords {
        private DkimRecord dkim;

        @Nullable public DkimRecord getDkim() { return dkim; }

        public static class DkimRecord {
            private String selector;

            @SerializedName("public")
            private String publicKey;

            private String headers;

            @Nullable public String getSelector() { return selector; }
            @Nullable public String getPublicKey() { return publicKey; }
            @Nullable public String getHeaders() { return headers; }
        }
    }

    /** Detected DNS provider information. */
    public static class DnsProvider {
        private String provider;

        @SerializedName("provider_label")
        private String providerLabel;

        private List<String> nameservers;
        private String error;

        @Nonnull public String getProvider() { return provider; }
        @Nonnull public String getProviderLabel() { return providerLabel; }
        @Nonnull public List<String> getNameservers() { return nameservers; }
        @Nullable public String getError() { return error; }
    }

    @Override
    public String toString() {
        return "Domain{domain='" + domain + "', status='" + status + "', canSend=" + canSend + '}';
    }
}
