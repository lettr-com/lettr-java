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
    private Integer bounceClass;

    @SerializedName("num_retries")
    private Integer numRetries;

    @SerializedName("queue_time")
    private Integer queueTime;

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

    @SerializedName("ip_address")
    private String ipAddress;

    @SerializedName("initial_pixel")
    private Boolean initialPixel;

    @SerializedName("outbound_tls")
    private String outboundTls;

    @SerializedName("device_token")
    private String deviceToken;

    private String fbtype;

    @SerializedName("report_by")
    private String reportBy;

    @SerializedName("report_to")
    private String reportTo;

    @SerializedName("remote_addr")
    private String remoteAddr;

    // Common getters — always present on full event objects (e.g. from /emails/events),
    // but may be null on list items from GET /emails (which uses a simpler schema).
    @Nonnull public String getEventId() { return eventId; }
    @Nonnull public String getType() { return type; }
    @Nonnull public String getTimestamp() { return timestamp; }
    @Nullable public String getRequestId() { return requestId; }
    @Nullable public String getRcptTo() { return rcptTo; }
    @Nullable public String getRawRcptTo() { return rawRcptTo; }
    @Nullable public String getRecipientDomain() { return recipientDomain; }
    @Nullable public String getMailboxProvider() { return mailboxProvider; }
    @Nullable public String getMailboxProviderRegion() { return mailboxProviderRegion; }

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
    /** Bounce classification code. Present on bounce, delay, out_of_band, policy_rejection events. */
    @Nullable public Integer getBounceClass() { return bounceClass; }
    @Nullable public Integer getNumRetries() { return numRetries; }
    /** Time spent in queue in milliseconds. Present on delivery and delay events. */
    @Nullable public Integer getQueueTime() { return queueTime; }
    /** Clicked URL. Present only on click and amp_click events. */
    @Nullable public String getTargetLinkUrl() { return targetLinkUrl; }
    @Nullable public String getTargetLinkName() { return targetLinkName; }
    /** Raw user-agent string. Present on click and open events. */
    @Nullable public String getUserAgent() { return userAgent; }
    /** Geolocation derived from event IP. Present on click and open events. */
    @Nullable public GeoIp getGeoIp() { return geoIp; }
    /** Parsed user-agent. Present on click and open events. */
    @Nullable public UserAgentParsed getUserAgentParsed() { return userAgentParsed; }
    /** IP address of the open/click. Present on click, open, initial_open, amp_click, amp_open, amp_initial_open events. */
    @Nullable public String getIpAddress() { return ipAddress; }
    /** Whether initial open tracking pixel was included. Present on injection, open, initial_open, amp_open, amp_initial_open events. */
    @Nullable public Boolean getInitialPixel() { return initialPixel; }
    /** Whether TLS was used for outbound delivery. Present on delivery and delay events. */
    @Nullable public String getOutboundTls() { return outboundTls; }
    /** Device token if applicable. Present on bounce and out_of_band events. */
    @Nullable public String getDeviceToken() { return deviceToken; }
    /** Feedback type (e.g. "abuse"). Present on spam_complaint events. */
    @Nullable public String getFbtype() { return fbtype; }
    /** Who reported the spam. Present on spam_complaint events. */
    @Nullable public String getReportBy() { return reportBy; }
    /** Where the spam report was sent. Present on spam_complaint events. */
    @Nullable public String getReportTo() { return reportTo; }
    /** Remote IP address. Present on policy_rejection events. */
    @Nullable public String getRemoteAddr() { return remoteAddr; }

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
