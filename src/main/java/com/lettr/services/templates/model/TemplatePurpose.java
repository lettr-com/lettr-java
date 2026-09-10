package com.lettr.services.templates.model;

import com.google.gson.annotations.SerializedName;

/**
 * The module a template belongs to.
 *
 * <p>The two do not mix: only {@link #CAMPAIGN} templates can be picked by the
 * campaign builder, and only {@link #TRANSACTIONAL} ones can be sent as single
 * emails. A template is filed into a folder of its own module.
 */
public enum TemplatePurpose {
    @SerializedName("transactional") TRANSACTIONAL,
    @SerializedName("campaign") CAMPAIGN;

    /** The wire value, for query parameters. */
    public String wireValue() {
        return this == CAMPAIGN ? "campaign" : "transactional";
    }
}
