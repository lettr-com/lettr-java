package com.lettr.core.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.lettr.core.exception.LettrApiException;
import com.lettr.core.exception.LettrException;
import com.lettr.core.exception.LettrValidationException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Internal HTTP client for communicating with the Lettr API.
 */
public class HttpClient {

    private static final String BASE_URL = "https://app.lettr.com/api";
    private static final String USER_AGENT = "lettr-java/0.1.0";
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
     * Perform a DELETE request.
     *
     * @param path API path (e.g. "/domains/example.com")
     * @throws LettrException on error
     */
    public void delete(String path) throws LettrException {
        String url = buildUrl(path, null);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(DEFAULT_TIMEOUT)
                .header("Authorization", "Bearer " + apiKey)
                .header("Accept", "application/json")
                .header("User-Agent", USER_AGENT)
                .DELETE()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();

            if (statusCode == 204) {
                return; // Success with no content
            }

            if (statusCode >= 400) {
                handleErrorResponse(statusCode, response.body());
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
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();
            String responseBody = response.body();

            if (statusCode >= 400) {
                handleErrorResponse(statusCode, responseBody);
            }

            if (responseBody == null || responseBody.isEmpty()) {
                return null;
            }

            JsonObject json = JsonParser.parseString(responseBody).getAsJsonObject();

            if (json.has("data")) {
                return gson.fromJson(json.get("data"), responseType);
            }

            return gson.fromJson(responseBody, responseType);
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

    private void handleErrorResponse(int statusCode, String responseBody) throws LettrException {
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

            throw new LettrApiException(message, statusCode, errorCode);
        } catch (LettrException e) {
            throw e;
        } catch (Exception e) {
            throw new LettrApiException(responseBody, statusCode, null);
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
}
