package com.lettr.core.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.lettr.core.exception.IdempotencyConflictException;
import com.lettr.core.exception.IdempotencyInProgressException;
import com.lettr.core.exception.LettrApiException;
import com.lettr.core.exception.LettrException;
import com.lettr.core.exception.LettrValidationException;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Internal HTTP client for communicating with the Lettr API.
 */
public class HttpClient {

    private static final String BASE_URL = "https://app.lettr.com/api";
    private static final String SDK_VERSION = loadVersion();
    private static final String USER_AGENT = "lettr-java/" + SDK_VERSION;
    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(30);

    private final String apiKey;
    private final java.net.http.HttpClient client;
    private final Gson gson;

    public HttpClient(String apiKey) {
        this.apiKey = apiKey;
        this.client = java.net.http.HttpClient.newBuilder()
                .connectTimeout(DEFAULT_TIMEOUT)
                .build();
        this.gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
                .create();
    }

    /**
     * Perform a GET request.
     *
     * @param path         API path (e.g. "/emails")
     * @param queryParams  optional query parameters
     * @param responseType the type to deserialize the "data" field into
     * @param <T>          response data type
     * @return deserialized response data
     * @throws LettrException on error
     */
    public <T> T get(String path, Map<String, String> queryParams, Type responseType) throws LettrException {
        String url = buildUrl(path, queryParams);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(DEFAULT_TIMEOUT)
                .header("Authorization", "Bearer " + apiKey)
                .header("Accept", "application/json")
                .header("User-Agent", USER_AGENT)
                .GET()
                .build();

        return execute(request, responseType);
    }

    /**
     * Perform a POST request with a JSON body.
     *
     * @param path         API path (e.g. "/emails")
     * @param body         request body object (will be serialized to JSON)
     * @param responseType the type to deserialize the "data" field into
     * @param <T>          response data type
     * @return deserialized response data
     * @throws LettrException on error
     */
    public <T> T post(String path, Object body, Type responseType) throws LettrException {
        String url = buildUrl(path, null);
        String jsonBody = gson.toJson(body);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(DEFAULT_TIMEOUT)
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("User-Agent", USER_AGENT)
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        return execute(request, responseType);
    }

    /**
     * Perform a POST request with a JSON body and extra request headers,
     * returning both the deserialized data and the response headers.
     *
     * <p>Separate from {@link #post(String, Object, Type)} rather than an
     * overload with nullable arguments, because only one endpoint needs it: the
     * {@code Idempotency-Replayed} response header on a send. Returning the
     * headers rather than storing them keeps concurrent calls from reading each
     * other's.
     *
     * @param path          API path
     * @param body          request body object
     * @param responseType  the type to deserialize the "data" field into
     * @param extraHeaders  headers merged over the defaults, or null
     * @param <T>           response data type
     * @return the deserialized data alongside the response headers
     * @throws LettrException on error
     */
    public <T> ApiResponse<T> postWithHeaders(String path, Object body, Type responseType,
                                              Map<String, String> extraHeaders) throws LettrException {
        String url = buildUrl(path, null);
        String jsonBody = gson.toJson(body);

        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(DEFAULT_TIMEOUT)
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("User-Agent", USER_AGENT);

        if (extraHeaders != null) {
            for (Map.Entry<String, String> entry : extraHeaders.entrySet()) {
                builder.header(entry.getKey(), entry.getValue());
            }
        }

        return executeWithHeaders(
                builder.POST(HttpRequest.BodyPublishers.ofString(jsonBody)).build(),
                responseType);
    }

    /**
     * A deserialized response together with the headers it arrived with.
     *
     * @param <T> the deserialized data type
     */
    public static final class ApiResponse<T> {
        private final T data;
        private final java.net.http.HttpHeaders headers;

        ApiResponse(T data, java.net.http.HttpHeaders headers) {
            this.data = data;
            this.headers = headers;
        }

        public T getData() { return data; }

        /** The first value of a response header, or null when absent. */
        public String header(String name) {
            return headers.firstValue(name).orElse(null);
        }
    }

    /**
     * Perform a POST request with no body, returning a deserialized response.
     * Used by endpoints whose only input is the path parameter (e.g.
     * {@code /campaigns/{id}/send}).
     *
     * @param path         API path
     * @param responseType the type to deserialize the "data" field into
     * @param <T>          response data type
     * @return deserialized response data
     * @throws LettrException on error
     */
    public <T> T post(String path, Type responseType) throws LettrException {
        String url = buildUrl(path, null);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(DEFAULT_TIMEOUT)
                .header("Authorization", "Bearer " + apiKey)
                .header("Accept", "application/json")
                .header("User-Agent", USER_AGENT)
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        return execute(request, responseType);
    }

    /**
     * Perform a PUT request with a JSON body.
     *
     * @param path         API path (e.g. "/webhooks/abc123")
     * @param body         request body object (will be serialized to JSON)
     * @param responseType the type to deserialize the "data" field into
     * @param <T>          response data type
     * @return deserialized response data
     * @throws LettrException on error
     */
    public <T> T put(String path, Object body, Type responseType) throws LettrException {
        String url = buildUrl(path, null);
        String jsonBody = gson.toJson(body);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(DEFAULT_TIMEOUT)
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("User-Agent", USER_AGENT)
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        return execute(request, responseType);
    }

    /**
     * Perform a POST request without expecting a deserialized response body.
     * Useful for endpoints that return only a {@code {"message": "..."}} envelope.
     *
     * @param path API path
     * @param body request body object (will be serialized to JSON, or {@code null} for no body)
     * @throws LettrException on error
     */
    public void post(String path, Object body) throws LettrException {
        String url = buildUrl(path, null);
        String jsonBody = gson.toJson(body);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(DEFAULT_TIMEOUT)
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("User-Agent", USER_AGENT)
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        executeNoResponse(request);
    }

    /**
     * Perform a PATCH request with a JSON body.
     *
     * @param path         API path
     * @param body         request body object (will be serialized to JSON)
     * @param responseType the type to deserialize the "data" field into
     * @param <T>          response data type
     * @return deserialized response data
     * @throws LettrException on error
     */
    public <T> T patch(String path, Object body, Type responseType) throws LettrException {
        String url = buildUrl(path, null);
        String jsonBody = gson.toJson(body);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(DEFAULT_TIMEOUT)
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("User-Agent", USER_AGENT)
                .method("PATCH", HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        return execute(request, responseType);
    }

    /**
     * Perform a DELETE request.
     *
     * @param path API path (e.g. "/domains/example.com")
     * @throws LettrException on error
     */
    public void delete(String path) throws LettrException {
        delete(path, (Map<String, String>) null);
    }

    /**
     * Perform a DELETE request with optional query parameters.
     *
     * @param path        API path
     * @param queryParams optional query parameters
     * @throws LettrException on error
     */
    public void delete(String path, Map<String, String> queryParams) throws LettrException {
        String url = buildUrl(path, queryParams);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(DEFAULT_TIMEOUT)
                .header("Authorization", "Bearer " + apiKey)
                .header("Accept", "application/json")
                .header("User-Agent", USER_AGENT)
                .DELETE()
                .build();

        executeNoResponse(request);
    }

    /**
     * Perform a DELETE request with a JSON body, returning a deserialized response.
     * Used by bulk delete endpoints that report counts.
     *
     * @param path         API path
     * @param body         request body object (will be serialized to JSON)
     * @param responseType the type to deserialize the "data" field into
     * @param <T>          response data type
     * @return deserialized response data
     * @throws LettrException on error
     */
    public <T> T delete(String path, Object body, Type responseType) throws LettrException {
        String url = buildUrl(path, null);
        String jsonBody = gson.toJson(body);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(DEFAULT_TIMEOUT)
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("User-Agent", USER_AGENT)
                .method("DELETE", HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        return execute(request, responseType);
    }

    /**
     * Percent-encode a single URL path segment so callers can safely interpolate
     * arbitrary identifiers ({@code /campaigns/" + encodePathSegment(id)}).
     * Encodes reserved characters including {@code /}, {@code ?}, {@code #},
     * and spaces; produces RFC 3986-compatible output.
     */
    public static String encodePathSegment(String segment) {
        if (segment == null) {
            return "";
        }
        // URLEncoder is form-encoded ('+' for space, '*' un-encoded); fix to RFC 3986.
        return java.net.URLEncoder.encode(segment, java.nio.charset.StandardCharsets.UTF_8)
                .replace("+", "%20")
                .replace("*", "%2A")
                .replace("%7E", "~");
    }

    private void executeNoResponse(HttpRequest request) throws LettrException {
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();

            if (statusCode == 204) {
                return; // Success with no content
            }

            if (statusCode >= 400) {
                handleErrorResponse(statusCode, response.body(), response.headers());
            }
        } catch (LettrException e) {
            throw e;
        } catch (IOException e) {
            throw new LettrException("Network error communicating with Lettr API", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new LettrException("Request was interrupted", e);
        }
    }

    private <T> T execute(HttpRequest request, Type responseType) throws LettrException {
        return this.<T>executeWithHeaders(request, responseType).getData();
    }

    private <T> ApiResponse<T> executeWithHeaders(HttpRequest request, Type responseType) throws LettrException {
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();
            String responseBody = response.body();

            if (statusCode >= 400) {
                handleErrorResponse(statusCode, responseBody, response.headers());
            }

            if (responseBody == null || responseBody.isEmpty()) {
                return new ApiResponse<>(null, response.headers());
            }

            JsonObject json = JsonParser.parseString(responseBody).getAsJsonObject();

            if (json.has("data")) {
                return new ApiResponse<>(gson.fromJson(json.get("data"), responseType), response.headers());
            }

            return new ApiResponse<>(gson.fromJson(responseBody, responseType), response.headers());
        } catch (LettrException e) {
            throw e;
        } catch (IOException e) {
            throw new LettrException("Network error communicating with Lettr API", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new LettrException("Request was interrupted", e);
        } catch (Exception e) {
            throw new LettrException("Failed to parse API response", e);
        }
    }

    private void handleErrorResponse(int statusCode, String responseBody,
                                     java.net.http.HttpHeaders headers) throws LettrException {
        if (responseBody == null || responseBody.isEmpty()) {
            throw new LettrApiException("API request failed with status " + statusCode, statusCode, null);
        }

        try {
            JsonObject json = JsonParser.parseString(responseBody).getAsJsonObject();
            String message = json.has("message") ? json.get("message").getAsString() : "Unknown error";
            String errorCode = json.has("error_code") ? json.get("error_code").getAsString() : null;

            // Handle validation errors (422)
            if (statusCode == 422 && json.has("errors")) {
                JsonObject errorsJson = json.getAsJsonObject("errors");
                Map<String, List<String>> errors = new HashMap<>();

                for (String key : errorsJson.keySet()) {
                    List<String> fieldErrors = new ArrayList<>();
                    for (JsonElement el : errorsJson.getAsJsonArray(key)) {
                        fieldErrors.add(el.getAsString());
                    }
                    errors.put(key, fieldErrors);
                }

                throw new LettrValidationException(message, errors);
            }

            // The two idempotency conflicts need telling apart: one is safe to
            // retry with the same key, the other will fail forever.
            if (statusCode == 409 && "idempotency_in_progress".equals(errorCode)) {
                throw new IdempotencyInProgressException(message, statusCode, errorCode, retryAfter(headers));
            }

            if (statusCode == 409 && "idempotency_key_conflict".equals(errorCode)) {
                throw new IdempotencyConflictException(message, statusCode, errorCode);
            }

            throw new LettrApiException(message, statusCode, errorCode);
        } catch (LettrException e) {
            throw e;
        } catch (Exception e) {
            throw new LettrApiException(responseBody, statusCode, null);
        }
    }

    /** {@code Retry-After} in seconds, or null when absent or unparseable. */
    private Integer retryAfter(java.net.http.HttpHeaders headers) {
        if (headers == null) {
            return null;
        }

        try {
            int seconds = Integer.parseInt(headers.firstValue("Retry-After").orElse(""));
            return seconds > 0 ? seconds : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private String buildUrl(String path, Map<String, String> queryParams) {
        StringBuilder url = new StringBuilder(BASE_URL).append(path);

        if (queryParams != null && !queryParams.isEmpty()) {
            url.append("?");
            boolean first = true;
            for (Map.Entry<String, String> entry : queryParams.entrySet()) {
                if (!first) {
                    url.append("&");
                }
                url.append(encodeParam(entry.getKey()))
                   .append("=")
                   .append(encodeParam(entry.getValue()));
                first = false;
            }
        }

        return url.toString();
    }

    private String encodeParam(String value) {
        return java.net.URLEncoder.encode(value, java.nio.charset.StandardCharsets.UTF_8);
    }

    /**
     * Returns the Gson instance for serialization.
     */
    public Gson getGson() {
        return gson;
    }

    private static String loadVersion() {
        try (InputStream in = HttpClient.class.getResourceAsStream("/com/lettr/version.properties")) {
            if (in == null) {
                return "unknown";
            }
            Properties props = new Properties();
            props.load(in);
            String v = props.getProperty("version");
            // Guard against unprocessed templates: "@version@", "${version}", or empty.
            if (v == null || v.isEmpty() || v.startsWith("@") || v.contains("${")) {
                return "unknown";
            }
            return v;
        } catch (IOException e) {
            return "unknown";
        }
    }
}
