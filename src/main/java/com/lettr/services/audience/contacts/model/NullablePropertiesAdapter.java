package com.lettr.services.audience.contacts.model;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Serializes a {@code Map<String, String>} preserving null values as JSON
 * {@code null} rather than dropping the entry.
 *
 * <p>The audience contact update API treats a property whose value is JSON
 * {@code null} as a deletion request. Gson's default behaviour drops null map
 * values, which would silently turn intended deletions into no-ops. This
 * adapter keeps them.</p>
 */
public class NullablePropertiesAdapter extends TypeAdapter<Map<String, String>> {

    @Override
    public void write(JsonWriter out, Map<String, String> value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }
        out.beginObject();
        for (Map.Entry<String, String> entry : value.entrySet()) {
            out.name(entry.getKey());
            if (entry.getValue() == null) {
                boolean prev = out.getSerializeNulls();
                out.setSerializeNulls(true);
                out.nullValue();
                out.setSerializeNulls(prev);
            } else {
                out.value(entry.getValue());
            }
        }
        out.endObject();
    }

    @Override
    public Map<String, String> read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        Map<String, String> result = new LinkedHashMap<>();
        in.beginObject();
        while (in.hasNext()) {
            String key = in.nextName();
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                result.put(key, null);
            } else {
                result.put(key, in.nextString());
            }
        }
        in.endObject();
        return result;
    }
}
