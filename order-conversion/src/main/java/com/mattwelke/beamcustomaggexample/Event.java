package com.mattwelke.beamcustomaggexample;

import org.apache.avro.reflect.Nullable;
import org.apache.beam.sdk.coders.AvroCoder;
import org.apache.beam.sdk.coders.DefaultCoder;

@DefaultCoder(AvroCoder.class)
public class Event {
    public String eventType;
    public String visitorId;
    @Nullable public SearchEvent searchEvent;
    @Nullable public OrderEvent orderEvent;
}
