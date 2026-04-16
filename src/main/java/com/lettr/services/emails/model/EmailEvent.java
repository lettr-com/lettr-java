package com.lettr.services.emails.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Represents a single email event (injection, delivery, bounce, open, click, etc.).
 *
 * <p>The {@code type} field determines which additional fields are present.
 * All event types share the common fields below; type-specific fields
 * (e.g. {@code bounceClass} for bounce events, {@code targetLinkUrl} for click events)
 * are null for other event types.</p>
 */
public class EmailEvent {

    // Common properties
    @SerializedName("event_id")
    private String eventId;

    private String type;
    private String timestamp;

    @SerializedName("request_id")
    private String requestId;

    @SerializedName("message_id")
    private String messageId;

    private String subject;

    @SerializedName("friendly_from")
    private String friendlyFrom;

    @SerializedName("sending_domain")
    private String sendingDomain;

    @SerializedName("rcpt_to")
    private String rcptTo;

    @SerializedName("raw_rcpt_to")
    private String rawRcptTo;

    @SerializedName("recipient_domain")
    private String recipientDomain;

    @SerializedName("mailbox_provider")
    private String mailboxProvider;

    @SerializedName("mailbox_provider_region")
    private String mailboxProviderRegion;

    @SerializedName("sending_ip")
    private String sendingIp;

    @SerializedName("click_tracking")
    private Boolean clickTracking;

    @SerializedName("open_tracking")
    private Boolean openTracking;

    private Boolean transactional;

    @SerializedName("msg_size")
    private Integer msgSize;

    @SerializedName("injection_time")
    private String injectionTime;

    @SerializedName("rcpt_meta")
    private Object rcptMeta;

    @SerializedName("campaign_id")
    private String campaignId;

    @SerializedName("template_id")
    private String templateId;

    @SerializedName("template_version")
    private String templateVersion;

    @SerializedName("ip_pool")
    private String ipPool;

    @SerializedName("msg_from")
    private String msgFrom;

    @SerializedName("rcpt_type")
    private String rcptType;

    @SerializedName("rcpt_tags")
    private List<String> rcptTags;

    @SerializedName("amp_enabled")
    private Boolean ampEnabled;

    @SerializedName("delv_method")
    private String delvMethod;

    @SerializedName("recv_method")
    private String recvMethod;

    @SerializedName("routing_domain")
    private String routingDomain;

    @SerializedName("scheduled_time")
    private String scheduledTime;

    @SerializedName("ab_test_id")
    private String abTestId;

    @SerializedName("ab_test_version")
    private String abTestVersion;

    // Type-specific fields
    private String reason;

    @SerializedName("raw_reason")
    private String rawReason;

    @SerializedName("error_code")
    private String errorCode;

    @SerializedName("bounce_class")
    private String bounceClass;

    @SerializedName("num_retries")
    private Integer numRetries;

    @SerializedName("queue_time")
    private String queueTime;

    @SerializedName("target_link_url")
    private String targetLinkUrl;

    @SerializedName("target_link_name")
    private String targetLinkName;

    @SerializedName("user_agent")
    private String userAgent;

    @SerializedName("geo_ip")
    private GeoIp geoIp;

    @SerializedName("user_agent_parsed")
    private UserAgentParsed userAgentParsed;

    // Common getters (always present per spec)
    @Nonnull public String getEventId() { return eventId; }
    @Nonnull public String getType() { return type; }
    @Nonnull public String getTimestamp() { return timestamp; }
    @Nonnull public String getRequestId() { return requestId; }
    @Nonnull public String getRcptTo() { return rcptTo; }
    @Nonnull public String getRawRcptTo() { return rawRcptTo; }
    @Nonnull public String getRecipientDomain() { return recipientDomain; }
    @Nonnull public String getMailboxProvider() { return mailboxProvider; }
    @Nonnull public String getMailboxProviderRegion() { return mailboxProviderRegion; }

    // Optional common fields
    @Nullable public String getMessageId() { return messageId; }
    @Nullable public String getSubject() { return subject; }
    @Nullable public String getFriendlyFrom() { return friendlyFrom; }
    @Nullable public String getSendingDomain() { return sendingDomain; }
    @Nullable public String getSendingIp() { return sendingIp; }
    @Nullable public Boolean getClickTracking() { return clickTracking; }
    @Nullable public Boolean getOpenTracking() { return openTracking; }
    @Nullable public Boolean getTransactional() { return transactional; }
    @Nullable public Integer getMsgSize() { return msgSize; }
    @Nullable public String getInjectionTime() { return injectionTime; }
    @Nullable public Object getRcptMeta() { return rcptMeta; }
    @Nullable public String getCampaignId() { return campaignId; }
    @Nullable public String getTemplateId() { return templateId; }
    @Nullable public String getTemplateVersion() { return templateVersion; }
    @Nullable public String getIpPool() { return ipPool; }
    @Nullable public String getMsgFrom() { return msgFrom; }
    @Nullable public String getRcptType() { return rcptType; }
    @Nullable public List<String> getRcptTags() { return rcptTags; }
    @Nullable public Boolean getAmpEnabled() { return ampEnabled; }
    @Nullable public String getDelvMethod() { return delvMethod; }
    @Nullable public String getRecvMethod() { return recvMethod; }
    @Nullable public String getRoutingDomain() { return routingDomain; }
    @Nullable public String getScheduledTime() { return scheduledTime; }
    @Nullable public String getAbTestId() { return abTestId; }
    @Nullable public String getAbTestVersion() { return abTestVersion; }

    // Type-specific getters (null for other event types)
    /** Bounce/delivery reason. Present on bounce, delay, out_of_band, etc. */
    @Nullable public String getReason() { return reason; }
    @Nullable public String getRawReason() { return rawReason; }
    @Nullable public String getErrorCode() { return errorCode; }
    /** Bounce classification code. Present only on bounce events. */
    @Nullable public String getBounceClass() { return bounceClass; }
    @Nullable public Integer getNumRetries() { return numRetries; }
    @Nullable public String getQueueTime() { return queueTime; }
    /** Clicked URL. Present only on click and amp_click events. */
    @Nullable public String getTargetLinkUrl() { return targetLinkUrl; }
    @Nullable public String getTargetLinkName() { return targetLinkName; }
    /** Raw user-agent string. Present on click and open events. */
    @Nullable public String getUserAgent() { return userAgent; }
    /** Geolocation derived from event IP. Present on click and open events. */
    @Nullable public GeoIp getGeoIp() { return geoIp; }
    /** Parsed user-agent. Present on click and open events. */
    @Nullable public UserAgentParsed getUserAgentParsed() { return userAgentParsed; }

    @Override
    public String toString() {
        return "EmailEvent{" +
                "eventId='" + eventId + '\'' +
                ", type='" + type + '\'' +
                ", rcptTo='" + rcptTo + '\'' +
                ", subject='" + subject + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
