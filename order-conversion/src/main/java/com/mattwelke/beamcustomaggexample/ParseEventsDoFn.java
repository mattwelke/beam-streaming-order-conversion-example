package com.mattwelke.beamcustomaggexample;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.beam.sdk.transforms.DoFn;
import org.slf4j.LoggerFactory;

public class ParseEventsDoFn extends DoFn<String, Event> {
    @ProcessElement
    public void processElement(@Element String input, OutputReceiver<Event> out) {
        try {
            Gson gson = new Gson();
            Event event = gson.fromJson(input, Event.class);
            out.output(event);
        } catch (JsonSyntaxException e) {
            LoggerFactory.getLogger(OrderConversion.class).error("Error parsing Event JSON: " + e.getMessage());
        }
    }
}
