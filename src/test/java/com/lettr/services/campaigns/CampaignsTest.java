package com.lettr.services.campaigns;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lettr.core.util.WireValues;
import com.lettr.services.campaigns.model.*;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CampaignsTest {

    private final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
            .create();

    @Test
    void campaignViewDeserializesWithStats() {
        String json = "{"
                + "\"id\":\"0193e6a8-1f3a-7c2a-b9e2-1aa1d2e5d3f0\","
                + "\"name\":\"Spring Newsletter\","
                + "\"subject\":\"Hello\","
                + "\"from_email\":\"news@example.com\","
                + "\"from_name\":\"Example\","
                + "\"reply_to\":null,"
                + "\"status\":\"sent\","
                + "\"scheduled_at\":null,"
                + "\"total_recipients\":1000,"
                + "\"sent_count\":980,"
                + "\"sent_at\":\"2026-05-20T10:00:00+00:00\","
                + "\"created_at\":\"2026-05-19T08:00:00+00:00\","
                + "\"html_content\":\"<p>Hi</p>\","
                + "\"stats\":{\"injections\":1000,\"deliveries\":980,\"bounces\":20,"
                + "\"spam_complaints\":1,\"opens\":500,\"unique_opens\":400,"
                + "\"clicks\":120,\"unique_clicks\":90,\"unsubscribes\":5}"
                + "}";

        CampaignView c = gson.fromJson(json, CampaignView.class);

        assertEquals("0193e6a8-1f3a-7c2a-b9e2-1aa1d2e5d3f0", c.getId());
        assertEquals("Spring Newsletter", c.getName());
        assertEquals("news@example.com", c.getFromEmail());
        assertNull(c.getReplyTo());
        assertEquals(CampaignStatus.SENT, c.getStatus());
        assertEquals(Integer.valueOf(1000), c.getTotalRecipients());
        assertEquals(980, c.getSentCount());
        assertEquals("<p>Hi</p>", c.getHtmlContent());

        CampaignStats stats = c.getStats();
        assertNotNull(stats);
        assertEquals(980, stats.getDeliveries());
        assertEquals(1, stats.getSpamComplaints());
        assertEquals(400, stats.getUniqueOpens());
        assertEquals(90, stats.getUniqueClicks());
    }

    @Test
    void campaignViewWithoutHtmlContentLeavesItNull() {
        String json = "{\"id\":\"abc\",\"name\":\"Draft\",\"status\":\"draft\","
                + "\"total_recipients\":null,\"sent_count\":0,"
                + "\"created_at\":\"2026-05-19T08:00:00+00:00\","
                + "\"stats\":{\"injections\":0,\"deliveries\":0,\"bounces\":0,"
                + "\"spam_complaints\":0,\"opens\":0,\"unique_opens\":0,"
                + "\"clicks\":0,\"unique_clicks\":0,\"unsubscribes\":0}}";

        CampaignView c = gson.fromJson(json, CampaignView.class);

        assertEquals(CampaignStatus.DRAFT, c.getStatus());
        assertNull(c.getHtmlContent());
        assertNull(c.getTotalRecipients());
        assertEquals(0, c.getSentCount());
    }

    @Test
    void listCampaignsResponseDeserializes() {
        String json = "{\"campaigns\":[{\"id\":\"abc\",\"name\":\"C1\",\"status\":\"draft\","
                + "\"sent_count\":0,\"created_at\":\"2026-05-19T08:00:00+00:00\","
                + "\"stats\":{\"injections\":0,\"deliveries\":0,\"bounces\":0,"
                + "\"spam_complaints\":0,\"opens\":0,\"unique_opens\":0,"
                + "\"clicks\":0,\"unique_clicks\":0,\"unsubscribes\":0}}],"
                + "\"pagination\":{\"total\":1,\"per_page\":20,\"current_page\":1,\"last_page\":1}}";

        ListCampaignsResponse response = gson.fromJson(json, ListCampaignsResponse.class);

        assertEquals(1, response.getCampaigns().size());
        assertEquals("C1", response.getCampaigns().get(0).getName());
        assertEquals(20, response.getPagination().getPerPage());
        assertEquals(1, response.getPagination().getLastPage());
    }

    @Test
    void listCampaignsResponseMissingCampaignsReturnsEmptyList() {
        ListCampaignsResponse response = gson.fromJson("{\"pagination\":{\"total\":0,\"per_page\":20,\"current_page\":1,\"last_page\":0}}", ListCampaignsResponse.class);
        assertNotNull(response.getCampaigns());
        assertTrue(response.getCampaigns().isEmpty());
    }

    @Test
    void campaignEventAndResponseDeserialize() {
        String json = "{\"events\":[{\"event_id\":\"evt_1\",\"event_type\":\"click\","
                + "\"email\":\"user@example.com\",\"timestamp\":\"2026-05-20T10:05:00+00:00\","
                + "\"bounce_class\":null,\"reason\":null,"
                + "\"target_link_url\":\"https://example.com\",\"user_agent\":\"Mozilla\"}],"
                + "\"next_cursor\":\"cursor-123\"}";

        ListCampaignEventsResponse response = gson.fromJson(json, ListCampaignEventsResponse.class);

        assertEquals(1, response.getEvents().size());
        CampaignEvent e = response.getEvents().get(0);
        assertEquals("evt_1", e.getEventId());
        assertEquals(CampaignEventType.CLICK, e.getEventType());
        assertEquals("https://example.com", e.getTargetLinkUrl());
        assertNull(e.getBounceClass());
        assertEquals("cursor-123", response.getNextCursor());
    }

    @Test
    void campaignEventsResponseNullCursorMeansLastPage() {
        String json = "{\"events\":[],\"next_cursor\":null}";

        ListCampaignEventsResponse response = gson.fromJson(json, ListCampaignEventsResponse.class);

        assertTrue(response.getEvents().isEmpty());
        assertNull(response.getNextCursor());
    }

    @Test
    void campaignEventsResponseMissingEventsReturnsEmptyList() {
        ListCampaignEventsResponse response = gson.fromJson("{\"next_cursor\":null}", ListCampaignEventsResponse.class);
        assertNotNull(response.getEvents());
        assertTrue(response.getEvents().isEmpty());
    }

    @Test
    void enumsRoundTripWithWireValues() {
        assertEquals(CampaignStatus.IN_REVIEW, gson.fromJson("\"in_review\"", CampaignStatus.class));
        assertEquals("in_review", WireValues.of(CampaignStatus.IN_REVIEW));
        assertEquals(CampaignEventType.SPAM_COMPLAINT, gson.fromJson("\"spam_complaint\"", CampaignEventType.class));
        assertEquals("spam_complaint", WireValues.of(CampaignEventType.SPAM_COMPLAINT));
        assertEquals("list_unsubscribe", WireValues.of(CampaignEventType.LIST_UNSUBSCRIBE));
    }

    @Test
    void listCampaignsParamsBuildQueryParams() {
        Map<String, String> params = ListCampaignsParams.builder()
                .page(2)
                .perPage(50)
                .status(CampaignStatus.SCHEDULED)
                .build()
                .toQueryParams();

        assertEquals("2", params.get("page"));
        assertEquals("50", params.get("per_page"));
        assertEquals("scheduled", params.get("status"));
    }

    @Test
    void listCampaignsParamsOmitsNulls() {
        Map<String, String> params = ListCampaignsParams.builder().build().toQueryParams();
        assertTrue(params.isEmpty());
    }

    @Test
    void listCampaignEventsParamsBuildQueryParams() {
        Map<String, String> params = ListCampaignEventsParams.builder()
                .eventType(CampaignEventType.OPEN)
                .email("user@example.com")
                .startDate("2026-05-01")
                .endDate("2026-05-31")
                .limit(100)
                .cursor("abc")
                .build()
                .toQueryParams();

        assertEquals("open", params.get("event_type"));
        assertEquals("user@example.com", params.get("email"));
        assertEquals("2026-05-01", params.get("start_date"));
        assertEquals("2026-05-31", params.get("end_date"));
        assertEquals("100", params.get("limit"));
        assertEquals("abc", params.get("cursor"));
    }

    @Test
    void scheduleOptionsRequiresScheduledAt() {
        assertThrows(IllegalArgumentException.class, () -> ScheduleCampaignOptions.of(null));
        assertThrows(IllegalArgumentException.class, () -> ScheduleCampaignOptions.of(""));
        assertEquals("2026-06-01T09:00:00+00:00",
                ScheduleCampaignOptions.of("2026-06-01T09:00:00+00:00").getScheduledAt());
    }

    @Test
    void getRequiresCampaignId() {
        Campaigns svc = new Campaigns("test-key");
        assertThrows(IllegalArgumentException.class, () -> svc.get(null));
        assertThrows(IllegalArgumentException.class, () -> svc.get(""));
    }

    @Test
    void listEventsRequiresCampaignId() {
        Campaigns svc = new Campaigns("test-key");
        assertThrows(IllegalArgumentException.class, () -> svc.listEvents(null));
        assertThrows(IllegalArgumentException.class, () -> svc.listEvents(""));
    }

    @Test
    void sendRequiresCampaignId() {
        Campaigns svc = new Campaigns("test-key");
        assertThrows(IllegalArgumentException.class, () -> svc.send(null));
        assertThrows(IllegalArgumentException.class, () -> svc.send(""));
    }

    @Test
    void unscheduleRequiresCampaignId() {
        Campaigns svc = new Campaigns("test-key");
        assertThrows(IllegalArgumentException.class, () -> svc.unschedule(null));
        assertThrows(IllegalArgumentException.class, () -> svc.unschedule(""));
    }

    @Test
    void scheduleRequiresCampaignIdAndOptions() {
        Campaigns svc = new Campaigns("test-key");
        ScheduleCampaignOptions valid = ScheduleCampaignOptions.of("2026-06-01T09:00:00+00:00");
        assertThrows(IllegalArgumentException.class, () -> svc.schedule(null, valid));
        assertThrows(IllegalArgumentException.class, () -> svc.schedule("", valid));
        assertThrows(IllegalArgumentException.class, () -> svc.schedule("campaign-id", null));
    }
}
