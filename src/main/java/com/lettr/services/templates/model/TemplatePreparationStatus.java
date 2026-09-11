package com.lettr.services.templates.model;

import com.google.gson.annotations.SerializedName;

/**
 * How far a template has got through preparation.
 *
 * <p>Creating or updating a template through the API defers image migration and
 * HTML rendering to a background job. On a create with JSON there is no HTML at
 * all until it finishes; on an <b>update</b> the previous render stays in place,
 * so the template is still sendable but is serving the <i>old</i> content.
 */
public enum TemplatePreparationStatus {
    @SerializedName("pending") PENDING,
    @SerializedName("ready") READY,
    @SerializedName("failed") FAILED;

    /**
     * Whether the content you last sent is the content that will go out.
     *
     * <p>Deliberately not named {@code isReady()}: this is not the same
     * question as "can I send this". A template being prepared after an update
     * keeps its previous render and stays sendable, so wiring this into a send
     * guard would block legitimate sends.
     */
    public boolean isSettled() {
        return this == READY;
    }
}
