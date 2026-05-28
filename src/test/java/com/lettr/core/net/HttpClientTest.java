package com.lettr.core.net;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class HttpClientTest {

    @Test
    void constructorAcceptsApiKey() {
        HttpClient client = new HttpClient("test-key");
        assertNotNull(client);
    }

    @Test
    void encodePathSegmentEscapesReservedCharacters() {
        // Reserved path characters must be percent-encoded so callers can safely
        // interpolate arbitrary identifiers.
        assertEquals("foo%2Fbar", HttpClient.encodePathSegment("foo/bar"));
        assertEquals("foo%3Fbar", HttpClient.encodePathSegment("foo?bar"));
        assertEquals("foo%23bar", HttpClient.encodePathSegment("foo#bar"));
        assertEquals("hello%20world", HttpClient.encodePathSegment("hello world"));
        assertEquals("100%25", HttpClient.encodePathSegment("100%"));
    }

    @Test
    void encodePathSegmentLeavesUuidUnchanged() {
        String uuid = "0193e6a8-1f3a-7c2a-b9e2-1aa1d2e5d3f0";
        assertEquals(uuid, HttpClient.encodePathSegment(uuid));
    }

    @Test
    void encodePathSegmentHandlesNull() {
        assertEquals("", HttpClient.encodePathSegment(null));
    }

    @Test
    void gsonInstanceAvailable() {
        HttpClient client = new HttpClient("test-key");
        Gson gson = client.getGson();
        assertNotNull(gson);
    }

    @Test
    void buildUrlWithoutParams() throws Exception {
        HttpClient client = new HttpClient("test-key");
        Method buildUrl = HttpClient.class.getDeclaredMethod("buildUrl", String.class, Map.class);
        buildUrl.setAccessible(true);

        String url = (String) buildUrl.invoke(client, "/emails", null);
        assertEquals("https://app.lettr.com/api/emails", url);
    }

    @Test
    void buildUrlWithParams() throws Exception {
        HttpClient client = new HttpClient("test-key");
        Method buildUrl = HttpClient.class.getDeclaredMethod("buildUrl", String.class, Map.class);
        buildUrl.setAccessible(true);

        Map<String, String> params = new LinkedHashMap<>();
        params.put("per_page", "25");
        params.put("cursor", "abc");

        String url = (String) buildUrl.invoke(client, "/emails", params);
        assertEquals("https://app.lettr.com/api/emails?per_page=25&cursor=abc", url);
    }

    @Test
    void buildUrlEncodesParams() throws Exception {
        HttpClient client = new HttpClient("test-key");
        Method buildUrl = HttpClient.class.getDeclaredMethod("buildUrl", String.class, Map.class);
        buildUrl.setAccessible(true);

        Map<String, String> params = new LinkedHashMap<>();
        params.put("recipients", "user@example.com");

        String url = (String) buildUrl.invoke(client, "/emails", params);
        assertTrue(url.contains("recipients=user%40example.com"));
    }

    @Test
    void buildUrlEmptyParams() throws Exception {
        HttpClient client = new HttpClient("test-key");
        Method buildUrl = HttpClient.class.getDeclaredMethod("buildUrl", String.class, Map.class);
        buildUrl.setAccessible(true);

        Map<String, String> params = new LinkedHashMap<>();
        String url = (String) buildUrl.invoke(client, "/emails", params);
        assertEquals("https://app.lettr.com/api/emails", url);
    }

    @Test
    void hasGetMethod() throws Exception {
        assertNotNull(HttpClient.class.getMethod("get", String.class, Map.class, java.lang.reflect.Type.class));
    }

    @Test
    void hasPostMethod() throws Exception {
        assertNotNull(HttpClient.class.getMethod("post", String.class, Object.class, java.lang.reflect.Type.class));
    }

    @Test
    void hasPutMethod() throws Exception {
        assertNotNull(HttpClient.class.getMethod("put", String.class, Object.class, java.lang.reflect.Type.class));
    }

    @Test
    void hasDeleteMethod() throws Exception {
        assertNotNull(HttpClient.class.getMethod("delete", String.class));
    }

    @Test
    void hasDeleteWithParamsMethod() throws Exception {
        assertNotNull(HttpClient.class.getMethod("delete", String.class, Map.class));
    }
}
