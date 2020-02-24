package com.mattwelke.beamcustomaggexample;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

public class FromVisitorIdKeyDoFn extends DoFn<KV<String, OrderSession>, OrderSession> {
    @ProcessElement
    public void processElement(@Element KV<String, OrderSession> input, OutputReceiver<OrderSession> out) {
        out.output(input.getValue());
    }
}
