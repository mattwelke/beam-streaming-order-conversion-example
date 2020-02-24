package com.mattwelke.beamcustomaggexample;

import org.apache.beam.sdk.coders.AvroCoder;
import org.apache.beam.sdk.coders.DefaultCoder;

@DefaultCoder(AvroCoder.class)
public class OrderEvent {
    public String productId;
}
