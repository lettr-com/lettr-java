package com.lettr.services.emails.model;

import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

/**
 * Lifecycle state of a scheduled email.
 *
 * <p>Lettr now owns the schedule itself and only hands the email to the sending
 * provider once it is due, so these are Lettr's own states - not the provider's
 * transmission states. A scheduled email never reports {@code delivered} or
 * {@code bounced}: once it is {@link #SENT} the per-recipient outcome lives on
 * the delivery events, keyed by
 * {@link ScheduledEmail#getTransmissionId() transmissionId}.
 *
 * <p>Wire values are defined by {@code @SerializedName}; use
 * {@link com.lettr.core.util.WireValues#of(Enum)} to retrieve them.
 */
@JsonAdapter(ScheduledEmailState.Adapter.class)
public enum ScheduledEmailState {
    @SerializedName("scheduled") SCHEDULED,
    @SerializedName("sending") SENDING,
    @SerializedName("sent") SENT,
    @SerializedName("cancelled") CANCELLED,
    @SerializedName("failed") FAILED,

    /*
     * The states below are the sending provider's, not Lettr's. Reading back a
     * legacy provider transmission id is answered from delivery events, which
     * report the provider's vocabulary - so these arrive on that path only,
     * never on a sch_ id.
     */

    /** @deprecated Legacy provider state, from a numeric transmission id. */
    @Deprecated @SerializedName("submitted") SUBMITTED,

    /** @deprecated Legacy provider state, from a numeric transmission id. */
    @Deprecated @SerializedName("generating") GENERATING,

    /** @deprecated Legacy provider state, from a numeric transmission id. */
    @Deprecated @SerializedName("delivered") DELIVERED,

    /** @deprecated Legacy provider state, from a numeric transmission id. */
    @Deprecated @SerializedName("bounced") BOUNCED,

    /** A state this version of the SDK does not know. */
    @SerializedName("unknown") UNKNOWN;

    /**
     * Whether a cancel would still stop the email going out.
     *
     * <p>Only {@link #SCHEDULED} qualifies: from {@link #SENDING} onwards the
     * email is already with the provider, which no longer accepts a recall.
     * This is a local pre-check, not a guarantee - the state you are holding
     * was read at some earlier moment, so a cancel can still lose the race and
     * fail.
     */
    public boolean isCancellable() {
        return this == SCHEDULED;
    }

    /**
     * Whether the state can still change. Polling a terminal state will never
     * report anything new.
     */
    public boolean isTerminal() {
        return this == SENT || this == CANCELLED || this == FAILED || this == DELIVERED || this == BOUNCED;
    }

    /**
     * Reads the wire value, answering {@link #UNKNOWN} for one this version
     * does not know.
     *
     * <p>Gson's default enum handling deserializes an unrecognised value to
     * {@code null}, which would break {@link ScheduledEmail#getState()}'s
     * {@code @Nonnull} contract - and a state the API adds later would then
     * turn every read into a surprise NPE.
     */
    static final class Adapter extends TypeAdapter<ScheduledEmailState> {
        @Override
        public void write(JsonWriter out, ScheduledEmailState value) throws IOException {
            if (value == null) {
                out.nullValue();
                return;
            }
            out.value(com.lettr.core.util.WireValues.of(value));
        }

        @Override
        public ScheduledEmailState read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return UNKNOWN;
            }
            String wire = in.nextString();
            for (ScheduledEmailState state : values()) {
                if (com.lettr.core.util.WireValues.of(state).equals(wire)) {
                    return state;
                }
            }
            return UNKNOWN;
        }
    }
}
