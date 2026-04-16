package com.lettr.services.emails.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nullable;

/**
 * Parsed user-agent information from an open/click event.
 * All fields may be null when parsing was unable to determine the value.
 */
public class UserAgentParsed {

    @SerializedName("agent_family")
    private String agentFamily;

    @SerializedName("device_brand")
    private String deviceBrand;

    @SerializedName("device_family")
    private String deviceFamily;

    @SerializedName("os_family")
    private String osFamily;

    @SerializedName("os_version")
    private String osVersion;

    @SerializedName("is_mobile")
    private Boolean isMobile;

    @SerializedName("is_proxy")
    private Boolean isProxy;

    @SerializedName("is_prefetched")
    private Boolean isPrefetched;

    @Nullable public String getAgentFamily() { return agentFamily; }
    @Nullable public String getDeviceBrand() { return deviceBrand; }
    @Nullable public String getDeviceFamily() { return deviceFamily; }
    @Nullable public String getOsFamily() { return osFamily; }
    @Nullable public String getOsVersion() { return osVersion; }
    @Nullable public Boolean getIsMobile() { return isMobile; }
    @Nullable public Boolean getIsProxy() { return isProxy; }
    /** Whether the open was prefetched by the email provider (Gmail proxy, Apple MPP). May not represent a real human open. */
    @Nullable public Boolean getIsPrefetched() { return isPrefetched; }

    @Override
    public String toString() {
        return "UserAgentParsed{agentFamily='" + agentFamily + "', osFamily='" + osFamily + "'}";
    }
}
