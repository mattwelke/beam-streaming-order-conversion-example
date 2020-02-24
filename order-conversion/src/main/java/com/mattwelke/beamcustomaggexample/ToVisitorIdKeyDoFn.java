package com.mattwelke.beamcustomaggexample;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

public class ToVisitorIdKeyDoFn extends DoFn<Event, KV<String, Event>> {
    @ProcessElement
    public void processElement(@Element Event input, OutputReceiver<KV<String, Event>> out) {
        out.output(KV.of(input.visitorId, input));
    }
}
