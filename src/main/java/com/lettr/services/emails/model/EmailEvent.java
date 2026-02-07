package com.lettr.services.emails.model;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

/**
 * Represents a single email event (injection, delivery, bounce, open, click, etc.).
 */
public class EmailEvent {

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
    private boolean clickTracking;

    @SerializedName("open_tracking")
    private boolean openTracking;

    private boolean transactional;

    @SerializedName("msg_size")
    private int msgSize;

    @SerializedName("injection_time")
    private String injectionTime;

    private String reason;

    @SerializedName("raw_reason")
    private String rawReason;

    @SerializedName("error_code")
    private String errorCode;

    @SerializedName("rcpt_meta")
    private Map<String, Object> rcptMeta;

    public String getEventId() { return eventId; }
    public String getType() { return type; }
    public String getTimestamp() { return timestamp; }
    public String getRequestId() { return requestId; }
    public String getMessageId() { return messageId; }
    public String getSubject() { return subject; }
    public String getFriendlyFrom() { return friendlyFrom; }
    public String getSendingDomain() { return sendingDomain; }
    public String getRcptTo() { return rcptTo; }
    public String getRawRcptTo() { return rawRcptTo; }
    public String getRecipientDomain() { return recipientDomain; }
    public String getMailboxProvider() { return mailboxProvider; }
    public String getMailboxProviderRegion() { return mailboxProviderRegion; }
    public String getSendingIp() { return sendingIp; }
    public boolean isClickTracking() { return clickTracking; }
    public boolean isOpenTracking() { return openTracking; }
    public boolean isTransactional() { return transactional; }
    public int getMsgSize() { return msgSize; }
    public String getInjectionTime() { return injectionTime; }
    public String getReason() { return reason; }
    public String getRawReason() { return rawReason; }
    public String getErrorCode() { return errorCode; }
    public Map<String, Object> getRcptMeta() { return rcptMeta; }

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
