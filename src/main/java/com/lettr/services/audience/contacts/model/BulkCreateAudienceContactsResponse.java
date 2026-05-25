package com.lettr.services.audience.contacts.model;

import com.google.gson.annotations.SerializedName;

public class BulkCreateAudienceContactsResponse {

    private int created;

    @SerializedName("already_existed")
    private int alreadyExisted;

    public int getCreated() {
        return created;
    }

    public int getAlreadyExisted() {
        return alreadyExisted;
    }

    @Override
    public String toString() {
        return "BulkCreateAudienceContactsResponse{created=" + created + ", alreadyExisted=" + alreadyExisted + '}';
    }
}
