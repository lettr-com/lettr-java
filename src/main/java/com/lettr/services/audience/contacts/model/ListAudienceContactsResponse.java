package com.lettr.services.audience.contacts.model;

import com.lettr.services.audience.model.AudiencePagination;

import javax.annotation.Nonnull;
import java.util.List;

public class ListAudienceContactsResponse {

    private List<AudienceContactView> contacts;
    private AudiencePagination pagination;

    @Nonnull
    public List<AudienceContactView> getContacts() {
        return contacts;
    }

    @Nonnull
    public AudiencePagination getPagination() {
        return pagination;
    }

    @Override
    public String toString() {
        return "ListAudienceContactsResponse{contacts=" + contacts + ", pagination=" + pagination + '}';
    }
}
