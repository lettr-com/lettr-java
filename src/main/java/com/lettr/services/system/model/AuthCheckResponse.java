package com.lettr.services.system.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;

/**
 * Response from the API key validation endpoint.
 */
public class AuthCheckResponse {

    @SerializedName("team_id")
    private int teamId;

    private String timestamp;

    public int getTeamId() { return teamId; }
    @Nonnull public String getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return "AuthCheckResponse{teamId=" + teamId + ", timestamp='" + timestamp + "'}";
    }
}
