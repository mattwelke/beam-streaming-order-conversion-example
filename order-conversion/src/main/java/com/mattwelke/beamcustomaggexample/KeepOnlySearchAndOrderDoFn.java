package com.mattwelke.beamcustomaggexample;

import org.apache.beam.sdk.transforms.DoFn;

public class KeepOnlySearchAndOrderDoFn extends DoFn<Event, Event> {
    @ProcessElement
    public void processElement(@Element Event input, OutputReceiver<Event> out) {
        if (input.eventType.equals("search") || input.eventType.equals("order")) {
            out.output(input);
        }
    }
}
