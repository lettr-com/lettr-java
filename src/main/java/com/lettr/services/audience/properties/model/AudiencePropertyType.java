package com.lettr.services.audience.properties.model;

import com.google.gson.annotations.SerializedName;

public enum AudiencePropertyType {
    @SerializedName("string") STRING,
    @SerializedName("number") NUMBER,
    @SerializedName("boolean") BOOLEAN,
    @SerializedName("date") DATE,
    @SerializedName("json") JSON
}
