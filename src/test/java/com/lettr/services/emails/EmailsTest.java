package com.lettr.services.emails;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lettr.services.emails.model.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class EmailsTest {

    private final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
            .create();

    // --- CreateEmailOptions builder tests ---

    @Test
    void createEmailOptionsRequiresFrom() {
        assertThrows(IllegalArgumentException.class, () ->
                CreateEmailOptions.builder()
                        .to("test@example.com")
                        .subject("Hello")
                        .html("<p>Hello</p>")
                        .build());
    }

    @Test
    void createEmailOptionsRequiresTo() {
        assertThrows(IllegalArgumentException.class, () ->
                CreateEmailOptions.builder()
                        .from("sender@example.com")
                        .subject("Hello")
                        .html("<p>Hello</p>")
                        .build());
    }

    @Test
    void createEmailOptionsRequiresContent() {
        assertThrows(IllegalArgumentException.class, () ->
                CreateEmailOptions.builder()
                        .from("sender@example.com")
                        .to("test@example.com")
                        .subject("Hello")
                        .build());
    }

    @Test
    void createEmailOptionsAllowsTemplateSlugWithoutSubject() {
        CreateEmailOptions options = CreateEmailOptions.builder()
                .from("sender@example.com")
                .to("test@example.com")
                .templateSlug("welcome-email")
                .build();
        assertNotNull(options);
        assertNull(options.getSubject());
        assertEquals("welcome-email", options.getTemplateSlug());
    }

    @Test
    void createEmailOptionsBuildsWithAllFields() {
        Map<String, String> headers = new HashMap<>();
        headers.put("X-Custom", "value");

        CreateEmailOptions options = CreateEmailOptions.builder()
                .from("sender@example.com")
                .fromName("Sender Name")
                .to("a@example.com", "b@example.com")
                .cc("cc@example.com")
                .bcc("bcc@example.com")
                .subject("Hello")
                .replyTo("reply@example.com")
                .replyToName("Reply Name")
                .html("<p>Hello</p>")
                .text("Hello")
                .ampHtml("<amp-html>Hello</amp-html>")
                .templateSlug("welcome")
                .templateVersion(2)
                .projectId(5)
                .tag("welcome-series")
                .headers(headers)
                .attachments(Attachment.builder().name("file.pdf").type("application/pdf").data("base64data").build())
                .substitutionData(Map.of("name", "John"))
                .metadata(Map.of("user_id", "123"))
                .options(EmailOptions.builder().clickTracking(true).openTracking(true).transactional(true).build())
                .build();

        assertEquals("sender@example.com", options.getFrom());
        assertEquals("Sender Name", options.getFromName());
        assertEquals(2, options.getTo().size());
        assertEquals(1, options.getCc().size());
        assertEquals(1, options.getBcc().size());
        assertEquals("Hello", options.getSubject());
        assertEquals("reply@example.com", options.getReplyTo());
        assertEquals("Reply Name", options.getReplyToName());
        assertEquals("<p>Hello</p>", options.getHtml());
        assertEquals("Hello", options.getText());
        assertEquals("welcome", options.getTemplateSlug());
        assertEquals(2, options.getTemplateVersion());
        assertEquals(5, options.getProjectId());
        assertEquals("welcome-series", options.getTag());
        assertEquals("value", options.getHeaders().get("X-Custom"));
        assertEquals(1, options.getAttachments().size());
    }

    @Test
    void createEmailOptionsToList() {
        CreateEmailOptions options = CreateEmailOptions.builder()
                .from("sender@example.com")
                .to(Arrays.asList("a@example.com", "b@example.com"))
                .subject("Hello")
                .html("<p>Hello</p>")
                .build();

        assertEquals(2, options.getTo().size());
    }

    @Test
    void createEmailOptionsSerializesCorrectFieldNames() {
        CreateEmailOptions options = CreateEmailOptions.builder()
                .from("sender@example.com")
                .fromName("Test Sender")
                .to("recipient@example.com")
                .subject("Hello")
                .html("<p>Hello</p>")
                .replyTo("reply@example.com")
                .replyToName("Reply")
                .ampHtml("<amp>Hello</amp>")
                .tag("test-tag")
                .build();

        String json = gson.toJson(options);
        assertTrue(json.contains("\"from_name\""));
        assertTrue(json.contains("\"reply_to\""));
        assertTrue(json.contains("\"reply_to_name\""));
        assertTrue(json.contains("\"amp_html\""));
        assertFalse(json.contains("\"fromName\""));
        assertFalse(json.contains("\"replyTo\""));
    }

    // --- EmailOptions builder tests ---

    @Test
    void emailOptionsBuildsWithAllFields() {
        EmailOptions options = EmailOptions.builder()
                .clickTracking(true)
                .openTracking(false)
                .transactional(true)
                .inlineCss(true)
                .performSubstitutions(false)
                .build();

        assertEquals(true, options.getClickTracking());
        assertEquals(false, options.getOpenTracking());
        assertEquals(true, options.getTransactional());
        assertEquals(true, options.getInlineCss());
        assertEquals(false, options.getPerformSubstitutions());
    }

    @Test
    void emailOptionsSerializesCorrectFieldNames() {
        EmailOptions options = EmailOptions.builder()
                .inlineCss(true)
                .performSubstitutions(false)
                .build();

        String json = gson.toJson(options);
        assertTrue(json.contains("\"inline_css\""));
        assertTrue(json.contains("\"perform_substitutions\""));
    }

    // --- ListEmailsParams tests ---

    @Test
    void listEmailsParamsToQueryParams() {
        ListEmailsParams params = ListEmailsParams.builder()
                .perPage(50)
                .cursor("abc123")
                .recipients("user@example.com")
                .from("2024-01-01")
                .to("2024-12-31")
                .build();

        Map<String, String> queryParams = params.toQueryParams();
        assertEquals("50", queryParams.get("per_page"));
        assertEquals("abc123", queryParams.get("cursor"));
        assertEquals("user@example.com", queryParams.get("recipients"));
        assertEquals("2024-01-01", queryParams.get("from"));
        assertEquals("2024-12-31", queryParams.get("to"));
    }

    @Test
    void listEmailsParamsEmptyToQueryParams() {
        ListEmailsParams params = ListEmailsParams.builder().build();
        Map<String, String> queryParams = params.toQueryParams();
        assertTrue(queryParams.isEmpty());
    }

    // --- ListEmailEventsParams tests ---

    @Test
    void listEmailEventsParamsToQueryParams() {
        ListEmailEventsParams params = ListEmailEventsParams.builder()
                .events(Arrays.asList("delivery", "bounce"))
                .recipients(Arrays.asList("a@example.com", "b@example.com"))
                .from("2024-01-01")
                .to("2024-12-31")
                .perPage(25)
                .cursor("cursor123")
                .transmissions("trans1")
                .bounceClasses("10,25")
                .build();

        Map<String, String> queryParams = params.toQueryParams();
        assertEquals("delivery,bounce", queryParams.get("events"));
        assertEquals("a@example.com,b@example.com", queryParams.get("recipients"));
        assertEquals("2024-01-01", queryParams.get("from"));
        assertEquals("2024-12-31", queryParams.get("to"));
        assertEquals("25", queryParams.get("per_page"));
        assertEquals("cursor123", queryParams.get("cursor"));
        assertEquals("trans1", queryParams.get("transmissions"));
        assertEquals("10,25", queryParams.get("bounce_classes"));
    }

    // --- ScheduleEmailOptions tests ---

    @Test
    void scheduleEmailOptionsRequiresScheduledAt() {
        assertThrows(IllegalArgumentException.class, () ->
                ScheduleEmailOptions.builder()
                        .from("sender@example.com")
                        .to("test@example.com")
                        .subject("Hello")
                        .html("<p>Hello</p>")
                        .build());
    }

    @Test
    void scheduleEmailOptionsBuildsWithScheduledAt() {
        ScheduleEmailOptions options = ScheduleEmailOptions.builder()
                .from("sender@example.com")
                .to("test@example.com")
                .subject("Hello")
                .html("<p>Hello</p>")
                .scheduledAt("2024-01-16T10:00:00Z")
                .build();

        assertEquals("2024-01-16T10:00:00Z", options.getScheduledAt());
        assertEquals("sender@example.com", options.getFrom());
    }

    @Test
    void scheduleEmailOptionsSerializesScheduledAt() {
        ScheduleEmailOptions options = ScheduleEmailOptions.builder()
                .from("sender@example.com")
                .to("test@example.com")
                .subject("Hello")
                .html("<p>Hello</p>")
                .scheduledAt("2024-01-16T10:00:00Z")
                .build();

        String json = gson.toJson(options);
        assertTrue(json.contains("\"scheduled_at\""));
        assertTrue(json.contains("2024-01-16T10:00:00Z"));
    }

    // --- EmailEvent deserialization tests ---

    @Test
    void emailEventDeserializesCommonFields() {
        String json = "{\"event_id\":\"evt1\",\"type\":\"delivery\",\"timestamp\":\"2024-01-15T10:31:00.000Z\"," +
                "\"request_id\":\"req1\",\"rcpt_to\":\"user@example.com\",\"subject\":\"Hello\"," +
                "\"sending_domain\":\"example.com\",\"click_tracking\":true,\"open_tracking\":false," +
                "\"transactional\":true,\"msg_size\":30823}";

        EmailEvent event = gson.fromJson(json, EmailEvent.class);
        assertEquals("evt1", event.getEventId());
        assertEquals("delivery", event.getType());
        assertEquals("req1", event.getRequestId());
        assertEquals("user@example.com", event.getRcptTo());
        assertEquals("Hello", event.getSubject());
        assertEquals(true, event.getClickTracking());
        assertEquals(false, event.getOpenTracking());
        assertEquals(true, event.getTransactional());
        assertEquals(30823, event.getMsgSize());
    }

    @Test
    void emailEventDeserializesBounceFields() {
        String json = "{\"event_id\":\"evt1\",\"type\":\"bounce\",\"timestamp\":\"2024-01-15T10:31:00.000Z\"," +
                "\"bounce_class\":\"10\",\"reason\":\"550 User not found\",\"raw_reason\":\"550 User not found\"," +
                "\"error_code\":\"550\"}";

        EmailEvent event = gson.fromJson(json, EmailEvent.class);
        assertEquals("bounce", event.getType());
        assertEquals("10", event.getBounceClass());
        assertEquals("550 User not found", event.getReason());
        assertEquals("550", event.getErrorCode());
    }

    @Test
    void emailEventDeserializesClickFields() {
        String json = "{\"event_id\":\"evt1\",\"type\":\"click\",\"timestamp\":\"2024-01-15T10:31:00.000Z\"," +
                "\"target_link_url\":\"https://example.com\",\"target_link_name\":\"Click here\"," +
                "\"user_agent\":\"Mozilla/5.0\"," +
                "\"geo_ip\":{\"country\":\"CZ\",\"city\":\"Prague\",\"latitude\":50.08,\"longitude\":14.41}," +
                "\"user_agent_parsed\":{\"agent_family\":\"Chrome\",\"os_family\":\"Windows\",\"is_mobile\":false}}";

        EmailEvent event = gson.fromJson(json, EmailEvent.class);
        assertEquals("click", event.getType());
        assertEquals("https://example.com", event.getTargetLinkUrl());
        assertEquals("Click here", event.getTargetLinkName());
        assertNotNull(event.getGeoIp());
        assertEquals("CZ", event.getGeoIp().getCountry());
        assertEquals("Prague", event.getGeoIp().getCity());
        assertNotNull(event.getUserAgentParsed());
        assertEquals("Chrome", event.getUserAgentParsed().getAgentFamily());
        assertEquals(false, event.getUserAgentParsed().getIsMobile());
    }

    @Test
    void emailEventNullableFieldsHandled() {
        String json = "{\"event_id\":\"evt1\",\"type\":\"injection\",\"timestamp\":\"2024-01-15T10:31:00.000Z\"," +
                "\"click_tracking\":null,\"msg_size\":null}";

        EmailEvent event = gson.fromJson(json, EmailEvent.class);
        assertNull(event.getClickTracking());
        assertNull(event.getMsgSize());
    }

    // --- Response deserialization tests ---

    @Test
    void createEmailResponseDeserializes() {
        String json = "{\"request_id\":\"12345\",\"accepted\":1,\"rejected\":0}";
        CreateEmailResponse response = gson.fromJson(json, CreateEmailResponse.class);
        assertEquals("12345", response.getRequestId());
        assertEquals(1, response.getAccepted());
        assertEquals(0, response.getRejected());
    }

    @Test
    void listEmailsResponseDeserializes() {
        String json = "{\"events\":{\"data\":[{\"event_id\":\"e1\",\"type\":\"injection\",\"timestamp\":\"2024-01-15T10:00:00Z\"}]," +
                "\"total_count\":1,\"from\":\"2024-01-01T00:00:00Z\",\"to\":\"2024-01-31T23:59:59Z\"," +
                "\"pagination\":{\"next_cursor\":null,\"per_page\":25}}}";

        ListEmailsResponse response = gson.fromJson(json, ListEmailsResponse.class);
        assertNotNull(response.getEvents());
        assertEquals(1, response.getEvents().getTotalCount());
        assertEquals(1, response.getEvents().getData().size());
        assertEquals("e1", response.getEvents().getData().get(0).getEventId());
        assertNull(response.getEvents().getPagination().getNextCursor());
        assertEquals(25, response.getEvents().getPagination().getPerPage());
    }

    @Test
    void getEmailResponseDeserializes() {
        String json = "{\"transmission_id\":\"123\",\"state\":\"delivered\",\"from\":\"sender@example.com\"," +
                "\"subject\":\"Hello\",\"recipients\":[\"user@example.com\"],\"num_recipients\":1," +
                "\"events\":[{\"event_id\":\"e1\",\"type\":\"delivery\",\"timestamp\":\"2024-01-15T10:31:00Z\"}]}";

        GetEmailResponse response = gson.fromJson(json, GetEmailResponse.class);
        assertEquals("123", response.getTransmissionId());
        assertEquals("delivered", response.getState());
        assertEquals("sender@example.com", response.getFrom());
        assertEquals("Hello", response.getSubject());
        assertEquals(1, response.getNumRecipients());
        assertEquals(1, response.getEvents().size());
    }

    @Test
    void scheduledEmailDeserializes() {
        String json = "{\"transmission_id\":\"123\",\"state\":\"submitted\"," +
                "\"scheduled_at\":\"2024-01-16T10:00:00+00:00\"," +
                "\"from\":\"sender@example.com\",\"subject\":\"Newsletter\"," +
                "\"recipients\":[\"user@example.com\"],\"num_recipients\":1,\"events\":[]}";

        ScheduledEmail response = gson.fromJson(json, ScheduledEmail.class);
        assertEquals("123", response.getTransmissionId());
        assertEquals("submitted", response.getState());
        assertEquals("2024-01-16T10:00:00+00:00", response.getScheduledAt());
        assertTrue(response.getEvents().isEmpty());
    }

    // --- Attachment builder tests ---

    @Test
    void attachmentBuilderRequiresAllFields() {
        assertThrows(IllegalArgumentException.class, () ->
                Attachment.builder().name("file.pdf").type("application/pdf").build());
        assertThrows(IllegalArgumentException.class, () ->
                Attachment.builder().name("file.pdf").data("base64").build());
        assertThrows(IllegalArgumentException.class, () ->
                Attachment.builder().type("application/pdf").data("base64").build());
    }

    @Test
    void attachmentBuilderBuildsCorrectly() {
        Attachment attachment = Attachment.builder()
                .name("invoice.pdf")
                .type("application/pdf")
                .data("JVBERi0xLjQ=")
                .build();

        assertEquals("invoice.pdf", attachment.getName());
        assertEquals("application/pdf", attachment.getType());
        assertEquals("JVBERi0xLjQ=", attachment.getData());
    }

    // --- Service argument validation tests ---

    @Test
    void emailsGetRequiresRequestId() {
        Emails emails = new Emails("test-key");
        assertThrows(IllegalArgumentException.class, () -> emails.get(null));
        assertThrows(IllegalArgumentException.class, () -> emails.get(""));
    }

    @Test
    void emailsGetScheduledRequiresTransmissionId() {
        Emails emails = new Emails("test-key");
        assertThrows(IllegalArgumentException.class, () -> emails.getScheduled(null));
        assertThrows(IllegalArgumentException.class, () -> emails.getScheduled(""));
    }

    @Test
    void emailsCancelScheduledRequiresTransmissionId() {
        Emails emails = new Emails("test-key");
        assertThrows(IllegalArgumentException.class, () -> emails.cancelScheduled(null));
        assertThrows(IllegalArgumentException.class, () -> emails.cancelScheduled(""));
    }
}
