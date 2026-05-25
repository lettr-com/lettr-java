package com.lettr.services.audience.properties.model;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Always emits the {@code fallback_value} field — including as JSON {@code null}
 * when the caller wants to clear it. Without this, Gson would drop the field
 * for a null fallback and the API would treat the PATCH as a no-op.
 */
class UpdateAudiencePropertyOptionsAdapter extends TypeAdapter<UpdateAudiencePropertyOptions> {

    @Override
    public void write(JsonWriter out, UpdateAudiencePropertyOptions value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }
        out.beginObject();
        out.name("fallback_value");
        String fallback = value.getFallbackValue();
        if (fallback == null) {
            boolean prev = out.getSerializeNulls();
            out.setSerializeNulls(true);
            out.nullValue();
            out.setSerializeNulls(prev);
        } else {
            out.value(fallback);
        }
        out.endObject();
    }

    @Override
    public UpdateAudiencePropertyOptions read(JsonReader in) throws IOException {
        throw new UnsupportedOperationException("UpdateAudiencePropertyOptions is request-only");
    }
}
