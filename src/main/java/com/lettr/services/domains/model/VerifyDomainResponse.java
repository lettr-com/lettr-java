package com.lettr.services.domains.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Response from verifying a sending domain's DNS configuration.
 */
public class VerifyDomainResponse {

    private String domain;

    @SerializedName("dkim_status")
    private String dkimStatus;

    @SerializedName("cname_status")
    private String cnameStatus;

    @SerializedName("dmarc_status")
    private String dmarcStatus;

    @SerializedName("spf_status")
    private String spfStatus;

    @SerializedName("is_primary_domain")
    private boolean isPrimaryDomain;

    @SerializedName("ownership_verified")
    private String ownershipVerified;

    private DnsVerification dns;
    private DmarcValidation dmarc;
    private SpfValidation spf;

    @Nonnull public String getDomain() { return domain; }
    /** {@code valid}, {@code unverified}, or {@code invalid}. */
    @Nonnull public String getDkimStatus() { return dkimStatus; }
    /** {@code valid}, {@code unverified}, {@code invalid}, or {@code not_applicable}. */
    @Nonnull public String getCnameStatus() { return cnameStatus; }
    /** {@code valid}, {@code invalid}, {@code missing}, or {@code unverified}. */
    @Nonnull public String getDmarcStatus() { return dmarcStatus; }
    /** {@code valid}, {@code invalid}, {@code missing}, or {@code unverified}. */
    @Nonnull public String getSpfStatus() { return spfStatus; }
    public boolean isPrimaryDomain() { return isPrimaryDomain; }
    @Nullable public String getOwnershipVerified() { return ownershipVerified; }
    @Nullable public DnsVerification getDns() { return dns; }
    @Nullable public DmarcValidation getDmarc() { return dmarc; }
    @Nullable public SpfValidation getSpf() { return spf; }

    /** DNS verification details. */
    public static class DnsVerification {
        @SerializedName("dkim_record")  private String dkimRecord;
        @SerializedName("cname_record") private String cnameRecord;
        @SerializedName("dkim_error")   private String dkimError;
        @SerializedName("cname_error")  private String cnameError;
        @SerializedName("dmarc_record") private String dmarcRecord;
        @SerializedName("dmarc_error")  private String dmarcError;
        @SerializedName("spf_record")   private String spfRecord;
        @SerializedName("spf_error")    private String spfError;

        @Nullable public String getDkimRecord() { return dkimRecord; }
        @Nullable public String getCnameRecord() { return cnameRecord; }
        @Nullable public String getDkimError() { return dkimError; }
        @Nullable public String getCnameError() { return cnameError; }
        @Nullable public String getDmarcRecord() { return dmarcRecord; }
        @Nullable public String getDmarcError() { return dmarcError; }
        @Nullable public String getSpfRecord() { return spfRecord; }
        @Nullable public String getSpfError() { return spfError; }
    }

    /** DMARC validation result. */
    public static class DmarcValidation {
        @SerializedName("is_valid")                    private boolean isValid;
        private String status;
        @SerializedName("found_at_domain")             private String foundAtDomain;
        private String record;
        private String policy;
        @SerializedName("subdomain_policy")            private String subdomainPolicy;
        private String error;
        @SerializedName("covered_by_parent_policy")    private boolean coveredByParentPolicy;

        public boolean isValid() { return isValid; }
        @Nonnull public String getStatus() { return status; }
        @Nullable public String getFoundAtDomain() { return foundAtDomain; }
        @Nullable public String getRecord() { return record; }
        /** DMARC policy: {@code none}, {@code quarantine}, or {@code reject}. */
        @Nullable public String getPolicy() { return policy; }
        @Nullable public String getSubdomainPolicy() { return subdomainPolicy; }
        @Nullable public String getError() { return error; }
        public boolean isCoveredByParentPolicy() { return coveredByParentPolicy; }
    }

    /** SPF validation result. */
    public static class SpfValidation {
        @SerializedName("is_valid")            private boolean isValid;
        private String status;
        private String record;
        private String error;
        @SerializedName("includes_sparkpost")  private boolean includesSparkpost;

        public boolean isValid() { return isValid; }
        @Nonnull public String getStatus() { return status; }
        @Nullable public String getRecord() { return record; }
        @Nullable public String getError() { return error; }
        public boolean isIncludesSparkpost() { return includesSparkpost; }
    }

    @Override
    public String toString() {
        return "VerifyDomainResponse{domain='" + domain + "', dkimStatus='" + dkimStatus + "'}";
    }
}
